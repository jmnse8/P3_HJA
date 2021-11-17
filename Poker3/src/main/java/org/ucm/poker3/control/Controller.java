package org.ucm.poker3.control;

import java.util.ArrayList;
import java.util.List;
import org.ucm.poker3.model.cartas.Board;
import org.ucm.poker3.model.cartas.Carta;
import org.ucm.poker3.model.cartas.Jugador;
import org.ucm.poker3.model.cartas.JugadorOmaha;
import org.ucm.poker3.model.cartas.MazoCartas;
import org.ucm.poker3.model.equity.Equity;
import org.ucm.poker3.model.observer.Observer;
import org.ucm.poker3.utils.Util;
import org.ucm.poker3.view.porcentajeFrame.PorcentajeFrame;

public class Controller {

    private MazoCartas mazo;
    private List<Observer> obs;
    private Equity e;
    private ArrayList<Jugador> jugadores;
    private ArrayList<JugadorOmaha> jugadoresOmaha;
    private Board board;
    private boolean modoNormal;

    public Controller() {
        mazo = new MazoCartas();
        obs = new ArrayList<Observer>();
        e = new Equity();
        board = new Board();
        jugadores = new ArrayList();
        jugadoresOmaha = new ArrayList();
        modoNormal = true;
    }

    public void addJugador(Jugador j) {
        jugadores.add(j);
        mazo.meteCarta(j.getCarta1().getNum(), Util.paloANum(j.getCarta1().getPalo()));
        mazo.meteCarta(j.getCarta2().getNum(), Util.paloANum(j.getCarta2().getPalo()));
        for (Observer o : obs) {
            o.meterCartaJug(j);
        }
    }

    public void addJugadorOmaha(JugadorOmaha j) {
        jugadoresOmaha.add(j);
        mazo.meteCarta(j.getCarta1().getNum(), Util.paloANum(j.getCarta1().getPalo()));
        mazo.meteCarta(j.getCarta2().getNum(), Util.paloANum(j.getCarta2().getPalo()));
        mazo.meteCarta(j.getCarta3().getNum(), Util.paloANum(j.getCarta3().getPalo()));
        mazo.meteCarta(j.getCarta4().getNum(), Util.paloANum(j.getCarta4().getPalo()));

    }

    public void setBoard(Board b) {
        if(!board.getListaCartas().isEmpty()){
            //borrar as cartas de la matriz
            //cambiar board
            //meter cartas en matriz
        }
        else{
            board = b;
        }
    }

    public MazoCartas getMazo() {
        return mazo;
    }

    public void addObserver(Observer o) {
        obs.add(o);
    }

    public void calculaEquity() {
        PorcentajeFrame pF = new PorcentajeFrame();
        pF.setVisible(true);

        Runnable runner = new Runnable() {
            public void run() {
                String[] info;
                e.calculateEquity(jugadores, mazo, board, pF);
                for (Observer o : obs) {
                    o.actualizaEquity(e.getPorcentajes());
                }
            }
        };
        Thread t = new Thread(runner, "Equity Code Executer");
        t.start();

    }

    public void reset() {

    }

    
    
    public void existeBoard(Board b) throws Exception {
        if (modoNormal) {
            for (Carta c : b.getListaCartas()) {
                for (Jugador j1 : jugadores) {
                    if (j1.getCarta1() == c) {
                        throw new Exception("Carta existente " + j1.getCarta1() + " del jugador " + j1.getNumJugador());
                    }
                    if (j1.getCarta2() == c) {
                        throw new Exception("Carta existente " + j1.getCarta2() + " del jugador " + j1.getNumJugador());
                    }
                }
            }
        }
        else{
            for (Carta c : b.getListaCartas()) {
                for (JugadorOmaha j1 : jugadoresOmaha) {
                    if (j1.getCarta1() == c) {
                        throw new Exception("Carta existente " + j1.getCarta1() + " del jugador " + j1.getNumJugador());
                    }
                    if (j1.getCarta2() == c) {
                        throw new Exception("Carta existente " + j1.getCarta2() + " del jugador " + j1.getNumJugador());
                    }
                    if (j1.getCarta3() == c) {
                        throw new Exception("Carta existente " + j1.getCarta3() + " del jugador " + j1.getNumJugador());
                    }
                    if (j1.getCarta4() == c) {
                        throw new Exception("Carta existente " + j1.getCarta4() + " del jugador " + j1.getNumJugador());
                    }
                }
            }
        }
    }

    public void existeJugadorOCartaN(Jugador j) throws Exception {
        for (Jugador j1 : jugadores) {
            if (j1.getNumJugador() == j.getNumJugador()) {
                throw new Exception("Jugador ya en mesa");
            }
            if (j1.getCarta1() == j.getCarta1()) {
                throw new Exception("Carta existente " + j1.getCarta1() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta1() == j.getCarta2()) {
                throw new Exception("Carta existente " + j1.getCarta1() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta2() == j.getCarta1()) {
                throw new Exception("Carta existente " + j1.getCarta2() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta2() == j.getCarta2()) {
                throw new Exception("Carta existente " + j1.getCarta2() + " del jugador " + j1.getNumJugador());
            }
        }
        for (Carta c : board.getListaCartas()) {
            if (c == j.getCarta1()) {
                throw new Exception("Carta existente " + c + " en el board");
            }
            if (c == j.getCarta2()) {
                throw new Exception("Carta existente " + c + " en el board");
            }

        }
    }

    public void existeJugadorOCartaO(JugadorOmaha j) throws Exception {
        for (JugadorOmaha j1 : jugadoresOmaha) {
            if (j1.getNumJugador() == j.getNumJugador()) {
                throw new Exception("Jugador ya en mesa");
            }

            if (j1.getCarta1() == j.getCarta1()) {
                throw new Exception("Carta existente " + j1.getCarta1() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta1() == j.getCarta2()) {
                throw new Exception("Carta existente " + j1.getCarta1() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta1() == j.getCarta3()) {
                throw new Exception("Carta existente " + j1.getCarta1() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta1() == j.getCarta4()) {
                throw new Exception("Carta existente " + j1.getCarta1() + " del jugador " + j1.getNumJugador());
            }

            if (j1.getCarta2() == j.getCarta1()) {
                throw new Exception("Carta existente " + j1.getCarta2() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta2() == j.getCarta2()) {
                throw new Exception("Carta existente " + j1.getCarta2() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta2() == j.getCarta3()) {
                throw new Exception("Carta existente " + j1.getCarta2() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta2() == j.getCarta4()) {
                throw new Exception("Carta existente " + j1.getCarta2() + " del jugador " + j1.getNumJugador());
            }

            if (j1.getCarta3() == j.getCarta1()) {
                throw new Exception("Carta existente " + j1.getCarta3() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta3() == j.getCarta2()) {
                throw new Exception("Carta existente " + j1.getCarta3() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta3() == j.getCarta3()) {
                throw new Exception("Carta existente " + j1.getCarta3() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta3() == j.getCarta4()) {
                throw new Exception("Carta existente " + j1.getCarta3() + " del jugador " + j1.getNumJugador());
            }

            if (j1.getCarta4() == j.getCarta1()) {
                throw new Exception("Carta existente " + j1.getCarta4() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta4() == j.getCarta2()) {
                throw new Exception("Carta existente " + j1.getCarta4() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta4() == j.getCarta3()) {
                throw new Exception("Carta existente " + j1.getCarta4() + " del jugador " + j1.getNumJugador());
            }
            if (j1.getCarta4() == j.getCarta4()) {
                throw new Exception("Carta existente " + j1.getCarta4() + " del jugador " + j1.getNumJugador());
            }
        }
        for (Carta c : board.getListaCartas()) {
            if (c == j.getCarta1()) {
                throw new Exception("Carta existente " + c + " en el board");
            }
            if (c == j.getCarta2()) {
                throw new Exception("Carta existente " + c + " en el board");
            }
            if (c == j.getCarta3()) {
                throw new Exception("Carta existente " + c + " en el board");
            }
            if (c == j.getCarta4()) {
                throw new Exception("Carta existente " + c + " en el board");
            }
        }
    }

    public void cambiaModo() {
        modoNormal = !modoNormal;
    }

    public boolean getModo() {
        return modoNormal;
    }
}