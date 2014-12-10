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

import java.util.Observable;
import net.mikc.tools.clusterssh.config.Config;
import net.mikc.tools.clusterssh.transport.RemoteSession;
import net.mikc.tools.clusterssh.exceptions.ConnectionException;
import net.mikc.tools.clusterssh.gui.dialogs.Alert;
import net.mikc.tools.clusterssh.gui.InputWindow;
import net.mikc.tools.clusterssh.gui.TerminalWindow;
import net.mikc.tools.clusterssh.transport.impl.JschSsh;

/**
 *
 * @author mikc
 */
public class AppInitializer {

    public AppInitializer() {
        try {
            CommHandler commHandler = new CommHandler();

            // Input Terminal Window
            InputWindow input = new InputWindow();

            // Register link between the CommHandler and input window
            Observable inputController = input.getObservable();
            inputController.addObserver(commHandler);

            for (RemoteSession session : Config.SessionList.get()) {
                // Terminal output windows
                final TerminalWindow window = new TerminalWindow(
                        session.getHost(),
                        session.getUser(),
                        Config.LookAndFeel.TERMINAL_WINDOW_SIZE.width,
                        Config.LookAndFeel.TERMINAL_WINDOW_SIZE.height
                );

                // Register output terminals as observers.
                //commHandler.addObserver(window);
                final JschSsh transportHandler = new JschSsh(session);
                transportHandler.addObserver(window);
                commHandler.addChannel(transportHandler);
            }

            commHandler.connect();
        } catch (ConnectionException ex) {
            new Alert(ex.getLocalizedMessage()).display();
            System.exit(-1);
        }

    }

}
