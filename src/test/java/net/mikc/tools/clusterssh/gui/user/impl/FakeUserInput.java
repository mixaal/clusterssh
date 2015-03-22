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
package net.mikc.tools.clusterssh.gui.user.impl;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.mikc.tools.clusterssh.config.Config;
import net.mikc.tools.clusterssh.events.InputWindowEvent;
import net.mikc.tools.clusterssh.gui.user.UserInput;
import net.mikc.tools.clusterssh.gui.window.Window;
import net.mikc.tools.clusterssh.gui.window.WindowFactory;
import net.mikc.tools.clusterssh.gui.window.WindowOptions;

public class FakeUserInput implements UserInput {
    private final EventBus messageBus;
    private final Window window;

    @Inject
    FakeUserInput(final WindowFactory windowFactory, @Assisted EventBus messageBus) {
        this.messageBus = messageBus;
        this.window = windowFactory.create("Input Window", Config.LookAndFeel.INPUT_WINDOW_SIZE, new WindowOptions(true, false));
    }


    @Override
    public void sendInput(String text) {
        System.out.println("sending command: "+text);
        this.messageBus.post(new InputWindowEvent(text, null));
    }
}
