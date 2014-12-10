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
package net.mikc.tools.clusterssh.transport;

/**
 *
 * @author mikc
 */
public final class RemoteSession {

    private static final Integer DEFAULT_SSH_PORT = 22;

    private final String host, user, password;
    private final Integer port;

    public RemoteSession(final String user, final String password, final String host, Integer port) {
        this.user = user;
        this.host = host;
        this.password = password;
        this.port = port;
    }

    public RemoteSession(final String user, final String password, final String host) {
        this(user, password, host, DEFAULT_SSH_PORT);
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public Integer getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public boolean askPassword() {
        boolean ask = (password == null);
        return ask;
    }

}
