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
package net.mikc.tools.clusterssh.gui;

import net.mikc.tools.clusterssh.gui.domain.Appearance;
import java.util.Observable;
import java.util.Observer;
import net.mikc.tools.clusterssh.config.Config;
import net.mikc.tools.clusterssh.transport.Channel;

/**
 *
 * @author mikc
 */
public final class TerminalWindow extends TextAreaWindow implements Observer {

    private final String title;
    private final String host, user;
    private final int width, height;

    private String userMark() {
        return (isRoot()) ? " # " : " $ ";
    }

    boolean isRoot() {
        return "root".equals(user);
    }

    private Appearance getAppearance() {
        return (isRoot())
                ? Config.LookAndFeel.ROOT_APPEARANCE
                : Config.LookAndFeel.USER_APPEARANCE;
    }

    public TerminalWindow(final String host, final String user, int width, int height) {
        super(user + "@" + host, width, height, false, false);
        this.title = getTitle();
        this.user = user;
        this.host = host;
        this.width = width;
        this.height = height;
        setAppearance(getAppearance());
    }

    public void appendText(final String text) {
        textArea.append(text);
    }

    public int getTerminalWidth() {
        return this.width;
    }

    public int getTerminalHeight() {
        return this.height;
    }

    public String getTerminalTitle() {
        return this.title;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Channel && arg instanceof String) {
            final String output = (String) arg;
            appendText(output);
        }
    }
}
