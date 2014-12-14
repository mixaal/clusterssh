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
package net.mikc.tools.clusterssh.transport.pump.impl;

import net.mikc.tools.clusterssh.transport.pump.Sender;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.mikc.tools.clusterssh.events.InputWindowEvent;
import net.mikc.tools.clusterssh.exceptions.ConnectionException;
import net.mikc.tools.clusterssh.transport.channel.Channel;

/**
 *
 * @author mikc
 */
public class ChannelSender implements Sender {

    private final List<Channel> channels = new ArrayList<>();

    public ChannelSender() {
    }

    public void addChannel(final Channel channel) {
        channels.add(channel);
    }

    public void connect() throws ConnectionException {
        for (final Channel channel : channels) {
            channel.connect();
        }
    }

    @Override
    public void send(final String command) {
        try {
            for (final Channel channel : channels) {
                channel.writeToChannel(command);
            }
        } catch (IOException ex) {

        }
    }

    @Subscribe
    public void onTerminalInput(final InputWindowEvent evt) {
        final String commandToExecute = evt.getText();
        send(commandToExecute);
    }

}
