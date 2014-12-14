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
package net.mikc.tools.clusterssh.gui.domain;

import java.awt.Color;
import java.awt.Font;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mikc
 */
public class AppearanceTest {

    public AppearanceTest() {
    }

    /**
     * Test of getForeground method, of class Appearance.
     */
    @Test
    public void testGetForeground() {
        System.out.println("getForeground");
        Appearance instance = new Appearance(Color.red, Color.darkGray);
        Color result = instance.getForeground();
        assertEquals(Color.red, result);
    }

    /**
     * Test of getBackground method, of class Appearance.
     */
    @Test
    public void testGetBackground() {
        System.out.println("getBackground");
        Appearance instance = new Appearance(null, Color.blue);
        Color result = instance.getBackground();
        assertEquals(Color.blue, result);
    }

    /**
     * Test of getFont method, of class Appearance.
     */
    @Test
    public void testGetFont() {
        System.out.println("getFont");
        Appearance instance = new Appearance(null, null, new Font("Verdana", Font.BOLD, 12));
        Font expResult = new Font("Verdana", Font.BOLD, 12);
        Font result = instance.getFont();
        assertEquals(expResult, result);
    }

}
