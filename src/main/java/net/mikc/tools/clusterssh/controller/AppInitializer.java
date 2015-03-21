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
package net.mikc.tools.clusterssh.controller;

import net.mikc.tools.clusterssh.databus.MessageBus;
import net.mikc.tools.clusterssh.transport.pump.impl.ChannelSender;
import com.google.common.eventbus.EventBus;
import net.mikc.tools.clusterssh.config.Config;
import net.mikc.tools.clusterssh.exceptions.ConnectionException;
import net.mikc.tools.clusterssh.gui.InputWindow;
import net.mikc.tools.clusterssh.gui.TerminalWindow;
import net.mikc.tools.clusterssh.gui.dialogs.Alert;
import net.mikc.tools.clusterssh.transport.channel.Channel;
import net.mikc.tools.clusterssh.transport.RemoteSession;
import net.mikc.tools.clusterssh.transport.channel.ChannelFactory;
import net.mikc.tools.clusterssh.transport.channel.impl.JschSsh;

/**
 *
 * @author mikc
 */
public class AppInitializer {

    public AppInitializer() {

        try {
            // create an event bus for the input window
            final EventBus inputMessageBus = MessageBus.get();

            // sends the data to all channels
            ChannelSender sender = new ChannelSender();

            // Connect input terminal window to the input message bus
            InputWindow input = new InputWindow(inputMessageBus);

            // Register sender on the input message bus
            inputMessageBus.register(sender);

            // Create output terminal windows
            for (RemoteSession session : Config.SessionList.get()) {
                // For each remote session create a separate message bus
                final EventBus sessionMessageBus = MessageBus.get();

                // Terminal output windows
                final TerminalWindow window = new TerminalWindow(
                        session.getHost(),
                        session.getUser(),
                        Config.LookAndFeel.TERMINAL_WINDOW_SIZE
                );

                // Register output terminals on the session message bus
                sessionMessageBus.register(window);

                // create transport channel with the message bus
                final Channel transportHandler = ChannelFactory.newChannel(session, sessionMessageBus);

                // connect sender with the channels - use MessageBus!
                sender.addChannel(transportHandler);
            }

            // and finally establish the connection
            sender.connect();
        } catch (ConnectionException ex) {
            new Alert(ex.getLocalizedMessage()).display();
            System.exit(-1);
        }

    }

}
