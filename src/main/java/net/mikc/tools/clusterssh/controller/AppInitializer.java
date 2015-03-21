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

import com.google.inject.Inject;
import net.mikc.tools.clusterssh.controller.connection.RemoteConnection;
import net.mikc.tools.clusterssh.controller.connection.RemoteConnectionFactory;
import net.mikc.tools.clusterssh.transport.pump.Sender;
import com.google.common.eventbus.EventBus;
import net.mikc.tools.clusterssh.exceptions.ConnectionException;
import net.mikc.tools.clusterssh.gui.InputWindow;
import net.mikc.tools.clusterssh.gui.dialogs.Alert;
import net.mikc.tools.clusterssh.transport.RemoteSession;

import java.util.Collection;

/**
 *
 * @author mikc
 */
public class AppInitializer {

    // event bus for the input window
    private final EventBus inputMessageBus;
    // sessions to connect with input terminal
    private final Collection<RemoteSession> remoteSessions;
    // factory responsible for linking input to remote targets
    private final RemoteConnectionFactory remoteConnectionFactory;
    // sender implementing the payload transportation between the terminal and remote targets
    private final Sender sender;

    @Inject
    AppInitializer(
            final EventBus inputMessageBus,
            final Collection<RemoteSession> remoteSessions,
            final RemoteConnectionFactory remoteConnectionFactory,
            final Sender sender
    ) {
        this.inputMessageBus = inputMessageBus;
        this.remoteSessions = remoteSessions;
        this.remoteConnectionFactory = remoteConnectionFactory;
        this.sender = sender;
    }

    public void start() {
        try {
            // Connect input terminal window to the input message bus
            InputWindow input = new InputWindow(inputMessageBus);

            // Register sender on the input message bus
            inputMessageBus.register(sender);

            // Create output terminal windows
            for (RemoteSession session : remoteSessions) {
                final RemoteConnection rc = remoteConnectionFactory.create(session, sender);
                rc.connect();
            }

            // and finally establish the connection
            sender.connect();
        } catch (ConnectionException ex) {
            new Alert(ex.getLocalizedMessage()).display();
            System.exit(-1);
        }

    }

}
