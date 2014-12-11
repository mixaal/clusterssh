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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import net.mikc.tools.clusterssh.exceptions.ConnectionException;
import net.mikc.tools.clusterssh.transport.Channel;
import net.mikc.tools.clusterssh.transport.Receiver;
import net.mikc.tools.clusterssh.transport.Sender;

/**
 *
 * @author mikc
 */
public class CommSender implements Observer, Sender {

    private final List<Channel> channels = new ArrayList<>();

    public CommSender() {
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

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof InputWindowObservable && arg instanceof String) {
            final String commandToExecute = (String) arg;
            send(commandToExecute);
        }
    }

}
