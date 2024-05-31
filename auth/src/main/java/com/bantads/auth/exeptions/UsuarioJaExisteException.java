package com.bantads.auth.exeptions;

public class UsuarioJaExisteException extends Exception{
    public UsuarioJaExisteException(String mensagem){
        super(mensagem);
    }
}
