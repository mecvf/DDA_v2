/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import exceptions.InvalidUserActionException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import modelo.JugadorRuleta;
import modelo.Mesa;
import modelo.Modelo;
import modelo.Numero;
import modelo.Proceso;

/**
 *
 * @author Moi
 */
public class ControladorMesa implements Observer {

    private Modelo modelo = Modelo.getInstancia();
    private Proceso proceso;
    private VistaMesa vista;
    private JugadorRuleta jugador;
    private Mesa mesa;
    
    public ControladorMesa(VistaMesa vista, Mesa m, JugadorRuleta jr){
        this.vista = vista;
        this.jugador = jr;
        this.mesa= m;
        this.proceso = (m.buscarRonda(m.getUltimaRonda())).getElProceso();
        vista.mostrar(mesa.getNumeros());
        modelo.addObserver(this);
        proceso.addObserver(this);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals(Proceso.EVENTO_ADD_SECONDS)){
            vista.mostrarSegundos(Proceso.getSegundos());
        }
        else if (arg.equals(Proceso.EVENTO_TIME_OUT)){
            //vista.mostrarSegundos(Proceso.getSegundos());
            System.out.println("Echar de mesa!!");
            
            if (jugador.sinApostarTresVeces()) vista.cerrarVentana("Ha pasado 3 rondas sin apostar");
        }
        else if(arg.equals(Modelo.EVENTO_TABLERO)){
            vista.mostrar(mesa.getNumeros());
            long tot = mesa.buscarRonda(mesa.getUltimaRonda()).totalApostadoRonda(jugador); // mas limpio??
            vista.mostrarTotalApostado(tot);
        }
        else if(arg.equals(Modelo.EVENTO_SORTEARNUMERO)){            
            buscarNumeroActual();
            if (!modelo.estaEnEspera(jugador, mesa))  vista.habilitar(true);
            mensajeRonda();
            mostrarSaldo();
        }
        else if (arg.equals(Modelo.EVENTO_NUEVO_JUGADOR_MESA_RULETA) || arg.equals(Modelo.EVENTO_SALIR_MESA)){
            vista.mostrarJugadores(modelo.getJugadoresPorMesa(mesa));
        }
        else if (arg.equals(Modelo.EVENTO_CHECK_SALDOS)){
            if (jugador.expulsado()) vista.cerrarVentana("Se le terminó el saldo");
        }
        else if(arg.equals(Modelo.EVENTO_ACTUALIZA_SALDOS))
            vista.mostrarSaldo(jugador.getJugador().getSaldo());
    }
    
    public void apostar(Numero n, String v) throws InvalidUserActionException { 
        modelo.apostar(mesa, n, v, jugador);
        vista.exitoApuesta();   
    }

    public void cargarJugadoresActivos() {
        ArrayList<JugadorRuleta> j = modelo.getJugadoresPorMesa(mesa);
        vista.mostrarJugadores(j);
    }

    public void buscarNumeroActual() {
        mostrarNumeroSorteado(modelo.ultNumeroSorteado(mesa));
    }
    
    private void mostrarNumeroSorteado(int i){
        vista.mostrarNumeroSorteado(i);
    }

    public void mostrarSaldo() {
        vista.mostrarSaldo(jugador.getJugador().getSaldo());
    }

    public void finalizarApuesta()  { 
        int sorteado = modelo.finalizarApuesta(mesa, jugador);
        if(sorteado!= -1){
            vista.habilitar(true);
            mesa.avisarCheckSaldo();
        }
        else{
            vista.habilitar(false);
            jugador.setApostado(true);
        }
    } 

    public void salirDeMesa() {
        modelo.salirDeMesaRuleta(jugador, mesa);
        eliminarObservador();
    }
    
    public void mensajeRonda(){
        String msj = (mesa.estaEnEspera(jugador)) ? "Espera..." : "Apostar";
        vista.mensajeRonda(msj);
    }
    
    public void eliminarObservador() {
        modelo.deleteObserver(this);
    }

    public void colorJugador(Color color) {
        vista.colorJugador(color);
    }
    public void verApuestas() {
        vista.verApuestas(jugador);
    }
}
