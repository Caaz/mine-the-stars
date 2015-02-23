package com.caaz.minethestars.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.caaz.minethestars.Game;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
            return new GwtApplicationConfiguration(480, 480);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new Game();
        }
}