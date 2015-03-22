/*
 * Copyright (C) 2014 Michal Conos
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.mikc.tools.clusterssh.transport.channel.impl;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.mikc.tools.clusterssh.events.OutputTerminalDataEvent;
import net.mikc.tools.clusterssh.exceptions.ConnectionException;
import net.mikc.tools.clusterssh.transport.RemoteSession;
import net.mikc.tools.clusterssh.transport.channel.Channel;

import java.io.IOException;


public class FakeSsh implements Channel {

    private final RemoteSession session;
    private final EventBus receiver;

    @Inject
    FakeSsh(@Assisted final RemoteSession session, @Assisted final EventBus receiver) {
        this.session = session;
        this.receiver = receiver;
    }

    @Override
    public void connect() throws ConnectionException {
        System.out.println("connect to: host="+session.getHost()+" user="+session.getUser()+ " passwd="+session.getPassword());
    }

    @Override
    public void writeToChannel(String command) throws IOException {
        System.out.println("writeToChannel: " + command);
        switch(command) {
            case "pwd":
                onDataArrival((this.session.getUser().equals("root")) ? "/root" : "/home/"+this.session.getUser());
                break;
            case "ls":
                onDataArrival("no files in directory");
                break;
            case "cat /etc/issue":
                onDataArrival("RedHat");
                break;
            default:
                onDataArrival("unimplemented command: "+command);
        }
    }

    @Override
    public void disconnect() {
        System.out.println("disconnect " + session.getUser()+" from: "+session.getHost());
    }

    @Override
    public void onDataArrival(String data) {
        System.out.println("onDataArrival: " + data);
        receiver.post(new OutputTerminalDataEvent<>(data));
    }

    @Override
    public RemoteSession getRemoteSession() {
        return session;
    }
}
