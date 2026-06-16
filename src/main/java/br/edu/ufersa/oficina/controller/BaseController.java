package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.ui.ScreenManager;

public abstract class BaseController {
    protected final ScreenManager screenManager;

    public BaseController(ScreenManager screenManager){
        this.screenManager = screenManager;
    }
}
