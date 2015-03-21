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
import java.io.IOException;
import java.text.MessageFormat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.mikc.tools.clusterssh.events.OutputTerminalDataEvent;
import net.mikc.tools.clusterssh.exceptions.ConnectionException;
import net.mikc.tools.clusterssh.transport.channel.Channel;
import net.mikc.tools.clusterssh.transport.RemoteSession;

/**
 *
 * @author mikc
 */
public class FakeChannel implements Channel {

    private final EventBus receiver;
    private final RemoteSession remoteSession;


    @Inject
    FakeChannel(@Assisted final RemoteSession session, @Assisted final EventBus receiver) {
        this.remoteSession = session;
        this.receiver = receiver;
    }

    @Override
    public void connect() throws ConnectionException {
        System.out.println("Supposed to connect.");
    }

    @Override
    public void writeToChannel(String command) throws IOException {
        System.out.println(MessageFormat.format("Flush command: {0}", command));
        onDataArrival(command);
    }

    @Override
    public void disconnect() {
        System.out.println("disconnecting.");
    }

    @Override
    public void onDataArrival(String data) {
        System.out.println(MessageFormat.format("DATA ARRIVED: {0}", data));
        receiver.post(new OutputTerminalDataEvent<>(data));
    }

    @Override
    public RemoteSession getRemoteSession() {
        return this.remoteSession;
    }
}
