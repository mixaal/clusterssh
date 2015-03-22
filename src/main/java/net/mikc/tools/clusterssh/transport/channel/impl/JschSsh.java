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
package net.mikc.tools.clusterssh.transport.channel.impl;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.mikc.tools.clusterssh.events.OutputTerminalDataEvent;
import net.mikc.tools.clusterssh.exceptions.ConnectionException;
import net.mikc.tools.clusterssh.gui.dialogs.AlertDialog;
import net.mikc.tools.clusterssh.gui.dialogs.PasswordDialogFactory;
import net.mikc.tools.clusterssh.gui.dialogs.PromptDialog;
import net.mikc.tools.clusterssh.transport.channel.Channel;
import net.mikc.tools.clusterssh.transport.RemoteSession;

/**
 *
 * @author mikc
 */
public class JschSsh implements Channel {

    private Session session;
    private com.jcraft.jsch.Channel channel;
    private final JSch jsch;
    private final RemoteSession remoteSession;
    private OutputStream channelOut;
    private final EventBus messageBus;
    private final PasswordDialogFactory passwordDialogFactory;
    private final PromptDialog promptDialog;
    private final AlertDialog alertDialog;

    public class ReadDataFromChannel implements Runnable {

        @Override
        public void run() {
            try {
                final InputStream in = channel.getInputStream();
                int SIZE = 1024;
                byte[] tmp = new byte[SIZE];
                while (true) {

                    while (in.available() > 0) {
                        int i = in.read(tmp, 0, SIZE);
                        if (i < 0) {
                            break;
                        }
                        onDataArrival(new String(tmp, 0, i));
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }

                }
            } catch (IOException ex) {

            }
        }
    }

    /**
     * Create a secure channel from a session and specify a controller receiver.
     *
     * @param remoteSession
     * @param receiver
     */
    @Inject
    public JschSsh(
            final PasswordDialogFactory passwordDialogFactory,
            final PromptDialog promptDialog,
            final AlertDialog alertDialog,
            @Assisted final RemoteSession remoteSession,
            @Assisted final EventBus messageBus) {
        this.jsch = new JSch();
        this.remoteSession = remoteSession;
        this.messageBus = messageBus;
        this.passwordDialogFactory = passwordDialogFactory;
        this.promptDialog = promptDialog;
        this.alertDialog = alertDialog;
    }

    @Override
    public void connect() throws ConnectionException {
        try {
            this.session = jsch.getSession(remoteSession.getUser(), remoteSession.getHost(), remoteSession.getPort());
            if (!remoteSession.askPassword()) {
                this.session.setPassword(remoteSession.getPassword());
            }
            this.session.setUserInfo(
                    new MyUserInfo(
                            this.passwordDialogFactory,
                            this.promptDialog,
                            this.alertDialog
                    )
            );
            this.session.connect();

            this.channel = this.session.openChannel("shell");
            this.channelOut = this.channel.getOutputStream();
            this.channel.connect();
            new Thread(new ReadDataFromChannel()).start();

        } catch (JSchException | IOException ex) {
            throw new ConnectionException(ex.getLocalizedMessage());
        }
    }

    @Override
    public void writeToChannel(final String command) throws IOException {
        this.channelOut.write((command + "\n").getBytes());
        this.channelOut.flush();
    }

    @Override
    public void onDataArrival(final String data) {
        messageBus.post(new OutputTerminalDataEvent<>(data));
    }

    @Override
    public void disconnect() {
        this.channel.disconnect();
        this.session.disconnect();

    }

    @Override
    public void finalize() throws Throwable {
        disconnect();
        super.finalize();
    }

    @Override
    public RemoteSession getRemoteSession() {
        return this.remoteSession;
    }

    class MyUserInfo implements UserInfo, UIKeyboardInteractive {

        private final PasswordDialogFactory passwordDialogFactory;
        private final PromptDialog promptDialog;
        private final AlertDialog alertDialog;

        MyUserInfo(
                final PasswordDialogFactory passwordDialogFactory,
                final PromptDialog promptDialog,
                final AlertDialog alertDialog
        ) {
            this.passwordDialogFactory = passwordDialogFactory;
            this.promptDialog = promptDialog;
            this.alertDialog = alertDialog;
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return this.passwordDialogFactory.create(remoteSession.getHost()).display().getPassword();
        }

        @Override
        public boolean promptPassword(String string) {
            return remoteSession.askPassword();
        }

        @Override
        public boolean promptPassphrase(String string) {
            return false;
        }

        @Override
        public boolean promptYesNo(String message) {
            return promptDialog.display(message).getValue();
        }

        @Override
        public void showMessage(String message) {
            this.alertDialog.display(message);
        }

        @Override
        public String[] promptKeyboardInteractive(String string, String string1, String string2, String[] strings, boolean[] blns) {
            return null;
        }

    }
}
