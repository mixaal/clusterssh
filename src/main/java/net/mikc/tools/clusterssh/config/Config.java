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
package net.mikc.tools.clusterssh.config;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Collection;
import net.mikc.tools.clusterssh.gui.domain.Appearance;
import net.mikc.tools.clusterssh.transport.RemoteSession;

/**
 *
 * @author mikc
 */
public class Config {

    public static class LookAndFeel {

        public static final Appearance ROOT_APPEARANCE = new Appearance(Color.red, Color.white);
        public static final Appearance USER_APPEARANCE = new Appearance(Color.blue, Color.lightGray);
        public static final Appearance INPUT_APPEARANCE = new Appearance(Color.blue, Color.white);

        public static final Dimension INPUT_WINDOW_SIZE = new Dimension(1000, 100);
        public static final Dimension TERMINAL_WINDOW_SIZE = new Dimension(800, 600);
    }

    public static class SessionList {

        private static final Collection<RemoteSession> sessions = Arrays.asList(
                //new RemoteSession("mikc", "secretpassword", "myhost"),
                //new RemoteSession("root", null, "myhost") // ask for password
                new RemoteSession("mikc", "baficekmujmaly8+eUnis2", "vellum.cz"),
                new RemoteSession("mikc", "baficekmujmaly8+eUnis2", "vellum.cz")
        );

        public static Collection<RemoteSession> get() {
            return sessions;
        }
    }
}
