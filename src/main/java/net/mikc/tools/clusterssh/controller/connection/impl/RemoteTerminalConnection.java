/*
 *   clusterssh - run ssh clients for on multiple sites.
 *
 *   Copyright (C) 2014  Michal Conos
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License along
 *   with this program; if not, write to the Free Software Foundation, Inc.,
 *   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package net.mikc.tools.clusterssh.controller.connection.impl;


import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.mikc.tools.clusterssh.config.Config;
import net.mikc.tools.clusterssh.controller.connection.RemoteConnection;
import net.mikc.tools.clusterssh.gui.user.UserTerminalFactory;
import net.mikc.tools.clusterssh.gui.user.UserTerminal;
import net.mikc.tools.clusterssh.transport.RemoteSession;
import net.mikc.tools.clusterssh.transport.channel.Channel;
import net.mikc.tools.clusterssh.transport.channel.ChannelFactory;
import net.mikc.tools.clusterssh.transport.pump.Sender;

public class RemoteTerminalConnection implements RemoteConnection {
    //remote session message bus
    private final EventBus eventBus;
    // remote session itself
    private final RemoteSession remoteSession;
    // corresponding remote channel for payload transport
    private final Channel channel;
    // Channel sender
    private final Sender channelSender;
    // Terminal window factory
    private final UserTerminalFactory userTerminalFactory;
    // terminal window itself
    private final UserTerminal userTerminal;

    @Inject
    RemoteTerminalConnection(
            final EventBus eventBus,
            final ChannelFactory channelFactory,
            final UserTerminalFactory userTerminalFactory,
            @Assisted final RemoteSession remoteSession,
            @Assisted final Sender channelSender
    ) {
        this.eventBus = eventBus;
        this.remoteSession = remoteSession;
        this.channelSender = channelSender;
        this.channel = channelFactory.create(remoteSession, eventBus);
        this.userTerminalFactory = userTerminalFactory;
        // Terminal output windows
        this.userTerminal = userTerminalFactory.create(
                remoteSession.getHost(),
                remoteSession.getUser(),
                Config.LookAndFeel.TERMINAL_WINDOW_SIZE
        );
    }

    @Override
    public UserTerminal getUserTerminal() {
        return this.userTerminal;
    }

    public void connect() {
        // Register output terminals on the session message bus
        eventBus.register(this.userTerminal);
        // connect sender with the channels - use MessageBus!
        channelSender.addChannel(channel);
    }
}
