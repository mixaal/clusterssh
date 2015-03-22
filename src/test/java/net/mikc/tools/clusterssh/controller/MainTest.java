package net.mikc.tools.clusterssh.controller;

import com.google.inject.Guice;
import net.mikc.tools.clusterssh.controller.AppInitializer;
import net.mikc.tools.clusterssh.controller.modules.TestUnitApplicationModule;
import net.mikc.tools.clusterssh.gui.user.InputWindow;
import net.mikc.tools.clusterssh.gui.window.Window;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Copyright (c) 2014, 2015, Oracle and/or its affiliates.
 * All rights reserved.
 * <p/>
 * 22.3.15 - created by mikc
 */
public class MainTest {

    @Test
    public void startApp() {
        AppInitializer app = Guice.createInjector(new TestUnitApplicationModule()).getInstance(AppInitializer.class);
        app.start();
        InputWindow input = app.getInputWindow();
        assertTrue(input!=null);
    }

    @Test
    public void testPwdCommand() {
        AppInitializer app = Guice.createInjector(new TestUnitApplicationModule()).getInstance(AppInitializer.class);
        app.start();
        InputWindow input = app.getInputWindow();
        assertTrue(input!=null);
        input.sendInput("pwd");
        Window userTerminal = app.getRemoteConnections().get(0).getTerminalWindow().getWindow();
        Window rootTerminal = app.getRemoteConnections().get(1).getTerminalWindow().getWindow();

        assertEquals("/home/mikc", userTerminal.getText());
        assertEquals("/root", rootTerminal.getText());
    }
}
