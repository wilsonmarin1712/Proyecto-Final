
package Juego;

import Controlador.ControladorTeclado;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Wmarin, Almartinez, WFlorez
 */
public class PanelJuego extends JPanel {

  // Componentes requeridos. ¡No quitar!
    private Timer gameTimer;
    private ControladorTeclado controller;
    // Controls size of game window and framerate
    private final int gameWidth = 800;
    private final int gameHeight = 800;
    private final int framesPerSecond = 120;

    // Contadores agregados
    Random r = new Random();
    private int puntaje = 0;
    private int nivel = 1;
    private int numeroDeVidas = 3;
    private int highScore;
    private int markerX, markerY;
    private static int vidaJefe = 30;
    File f = new File("Highscore.txt");

    // Objetos añadidos

    private Enviar nave;
    private Enviar singleLife;
    private Enviar bonusEnemy;
    private Enemigo enemy;
    private Proteccion shield;
    private Bala bullet;
    private Beam beam, beam2, beam3;

    // Booleanos agregados
    private boolean newBulletCanFire = true;
    private boolean newBeamCanFire = true;
    private boolean newBonusEnemy = true;
    private boolean hitMarker = false;

    // Listas de Arreglos Agregados
    private ArrayList<Enviar> lifeList = new ArrayList();
    private ArrayList<Enviar> bonusEnemyList = new ArrayList();
    private ArrayList<Enemigo> enemyList = new ArrayList();
    private ArrayList<Proteccion> shieldList = new ArrayList();
    private ArrayList<Beam> beamList = new ArrayList();
    private ImageIcon background = new ImageIcon("images/backgroundSkin.jpg");

    // Se agregaron archivos de audio y transmisiones
    private File beamSound = new File("sounds/alienBeam.wav");
    private File bulletSound = new File("sounds/bulletSound.wav");
    private File levelUpSound = new File("sounds/levelUpSound.wav");
    private File deathSound = new File("sounds/deathSound.wav");
    private File hitmarkerSound = new File("sounds/hitmarkerSound.wav");
    private File shieldSound = new File("sounds/shieldSound.wav");
    private File bossSound = new File("sounds/bossSound.wav");
    private File bonusSound = new File("sounds/bonusSound.wav");
     private File damageSound = new File("sounds/damageSound.wav");
    private AudioStream beamSoundAudio;
    private InputStream beamSoundInput;
    private AudioStream bulletSoundAudio;
    private InputStream bulletSoundInput;
    private AudioStream levelUpSoundAudio;
    private InputStream levelUpSoundInput;
    private AudioStream deathSoundAudio;
    private InputStream deathSoundInput;
    private AudioStream hitSoundAudio;
    private InputStream hitSoundInput;
    private AudioStream shieldSoundAudio;
    private InputStream shieldSoundInput;
    private AudioStream bossSoundAudio;
    private InputStream bossSoundInput;
    private AudioStream bonusSoundAudio;
    private InputStream bonusSoundInput;
    private AudioStream damageSoundAudio;
    private InputStream damageSoundInput;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MÉTODOS ADICIONALES

// Usado en la clase Enemy para ayudar con el método de dibujar para el jefe
    public static int getBossHealth() {
        return vidaJefe;
    }
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // JUEGO DE CONFIGURACIÓN

    public final void setupGame() {

      // Establece enemigos para niveles normales
        if (nivel != 3 && nivel != 6 && nivel != 9 && nivel != 12) {
            // Six rows
            for (int row = 0; row < 6; row++) {
                // 5 columns
                for (int column = 0; column < 5; column++) {
                    enemy = new Enemigo((20 + (row * 100)), (20 + (column * 60)), nivel, 0, column, null, 40, 40); // La velocidad del enemigo aumentará cada nivel
                    enemyList.add(enemy);
                }
            }
        }
       // Establece enemigos para niveles de jefe
        if (nivel == 3 || nivel == 6 || nivel == 9 || nivel == 12) {
            AudioPlayer.player.start(bossSoundAudio); // Plays boss roar
            enemy = new Enemigo(20, 20, 3, 0, 100, null, 150, 150);
            enemyList.add(enemy);
        }
        // Gives directions on level 1
        if (nivel == 1) {
            JOptionPane.showMessageDialog(null, "Bienvenido a Galaxy!\n\nManual:\n\n- Usar las teclas izquierda/derecha para moverse\n- Presione la barra espaciadora para disparar\n- Los Aliens son mas rapidos cada nivel"
                    + "\n- BOSS cada 3 niveles\n- Un enemigo extra aparecera al azar\n- disparales para puntos extra!\n- Presiona R para reiniciar el alto puntaje\n\nDIVIERTETE!");
        }
        // Restablece todo el movimiento del controlador
        controller.resetController();

        // Establece los valores del barco del jugador  
        nave = new Enviar(375, 730, null, controller);

        // Establece el contador de vida.
        for (int column = 0; column < numeroDeVidas; column++) {
            singleLife = new Enviar(48 + (column * 20), 10, Color.WHITE, null);
            lifeList.add(singleLife);
        }

        // Establece los valores para 3 filas y 3 columnas de escudos
        for (int row = 0;
                row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                shield = new Proteccion(100 + (column * 250), 650 - (row * 10), 70, 10, Color.RED);
                shieldList.add(shield);
            }
        }
    }
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// PINTAR
    @Override
    public void paint(Graphics g) {

        // Establece la imagen de fondo
        background.paintIcon(null, g, 0, -150);

        // makes a string that says "+100" on enemy hit
        if (bullet != null) {
            if (hitMarker) {
                g.setColor(Color.WHITE);
                if (nivel != 3 && nivel != 6 && nivel != 9 && nivel != 12) {
                    g.drawString("+ 100", markerX + 20, markerY -= 1);
                } else {
                    g.drawString("- 1", markerX + 75, markerY += 1);
                }
            }
        }
         // Dibuja la nave del jugador
        nave.draw(g);

        // Dibuja 3 escudos espaciados uniformemente
        for (int index = 0; index < shieldList.size(); index++) {
            shieldList.get(index).draw(g);
        }

        // Dibuja 3 tipos diferentes de alienígenas
        try {
            for (int index = 0; index < enemyList.size(); index++) {
                enemyList.get(index).draw(g);
            }

        } catch (IndexOutOfBoundsException e) {
        }

        // Dibujar una viñeta en la barra espaciadora presionar
        if (controller.getKeyStatus(32)) {
            if (newBulletCanFire) {
                bullet = new Bala(nave.getXPosition() + 22, nave.getYPosition() - 20, 0, Color.RED);
                AudioPlayer.player.start(bulletSoundAudio); // Plays bullet sound
                newBulletCanFire = false;
            }
        }
        // Solo intenta dibujar viñeta después de presionar la tecla
        if (bullet != null) {
            bullet.draw(g);
        }

        // Genera haces aleatorios de enemigos
        if (nivel != 3 && nivel != 6 && nivel != 9 && nivel != 12) {
            if (newBeamCanFire) {
                for (int index = 0; index < enemyList.size(); index++) {
                    if (r.nextInt(30) == index) {
                        beam = new Beam(enemyList.get(index).getXPosition(), enemyList.get(index).getYPosition(), 0, Color.YELLOW);
                        beamList.add(beam);
                        AudioPlayer.player.start(beamSoundAudio); // Reproduce el sonido del haz para enemigos normales
                    }
                    newBeamCanFire = false;
                }
            }
        }
         // Genera haces a un ritmo más rápido para el jefe
        if (nivel == 3 || nivel == 6 || nivel == 9 || nivel == 12) {
            if (newBeamCanFire) {
                for (int index = 0; index < enemyList.size(); index++) {
                    if (r.nextInt(5) == index) {
                        beam = new Beam(enemyList.get(index).getXPosition() + 75, enemyList.get(index).getYPosition() + 140, 0, Color.YELLOW);
                        beam2 = new Beam(enemyList.get(index).getXPosition(), enemyList.get(index).getYPosition() + 110, 0, Color.YELLOW);
                        beam3 = new Beam(enemyList.get(index).getXPosition() + 150, enemyList.get(index).getYPosition() + 110, 0, Color.YELLOW);
                        beamList.add(beam);
                        beamList.add(beam2);
                        beamList.add(beam3);
                        AudioPlayer.player.start(beamSoundAudio); // Reproduce el sonido del haz para el jefe
                    }
                    newBeamCanFire = false;
                }
            }
        }
        // Dibuja los rayos generados
        for (int index = 0; index < beamList.size(); index++) {
            beamList.get(index).draw(g);
        }
        // Genera un bonus bonus al azar
        if (newBonusEnemy) {
            if (r.nextInt(3000) == 1500) {
                bonusEnemy = new Enviar(-50, 30, Color.RED, null);
                bonusEnemyList.add(bonusEnemy);
                newBonusEnemy = false;
            }
        }
        // Dibuja un enemigo extra
        for (int index = 0; index < bonusEnemyList.size(); index++) {
            bonusEnemyList.get(index).bonusDraw(g);
        }

       // Establece la visualización de puntuación
        g.setColor(Color.WHITE);
        g.drawString("Puntaje: " + puntaje, 260, 20);

       // Establece la pantalla del contador de vida
        g.setColor(Color.WHITE);
        g.drawString("Vidas:", 11, 20);
        for (int index = 0; index < lifeList.size(); index++) {
            lifeList.get(index).lifeDraw(g);
        }
         // establece la visualización de nivel
        g.setColor(Color.WHITE);
        g.drawString("Nivel " + nivel, 750, 20);

        // Establece una alta puntuación
        g.setColor(Color.WHITE);
        g.drawString("PuntajeAlto: " + highScore, 440, 20);

        // Dibuja una visualización de salud para el nivel de jefe
        if (nivel == 3 || nivel == 6 || nivel == 9 || nivel == 12) {
            g.setColor(Color.WHITE);
            g.drawString("Salud Boss: " + vidaJefe, 352, 600);
        }
    }
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ACTUALIZAR EL ESTADO DEL JUEGO
    
    public void updateGameState(int frameNumber) {

        // Permite al jugador moverse hacia la izquierda y hacia la derecha

        nave.move();

        // Actualizaciones de puntuación más alta
        try {
            Scanner fileScan = new Scanner(f);
            while (fileScan.hasNextInt()) {
                String nextLine = fileScan.nextLine();
                Scanner lineScan = new Scanner(nextLine);
                highScore = lineScan.nextInt();
            }
        } catch (FileNotFoundException e) {
        }
        // Agrega la opción para restablecer el puntaje alto
        if (controller.getKeyStatus(82)) {
            int answer = JOptionPane.showConfirmDialog(null, "Quiere reiniciar el alto puntaje?", ":)", 0);
            controller.resetController();
            if (answer == 0) {
                try {
                    String scoreString = Integer.toString(0);
                    PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
                    pw.write(scoreString);
                    pw.close();
                } catch (FileNotFoundException e) {
                }
            }
        }
        // Actualiza el archivo de texto de puntuación más alta si su puntaje excede el puntaje más alto anterior
        try {
            if (puntaje > highScore) {
                String scoreString = Integer.toString(puntaje);
                PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
                pw.write(scoreString);
                pw.close();
            }
        } catch (FileNotFoundException e) {
        }

   // Makes enemies move and change direction at borders
        if ((enemyList.get(enemyList.size() - 1).getXPosition() + enemyList.get(enemyList.size() - 1).getXVelocity()) > 760 || (enemyList.get(0).getXPosition() + enemyList.get(0).getXVelocity()) < 0) {
            for (int index = 0; index < enemyList.size(); index++) {
                enemyList.get(index).setXVelocity(enemyList.get(index).getXVelocity() * -1);
                enemyList.get(index).setYPosition(enemyList.get(index).getYPosition() + 10);
            }
        } else {
            for (int index = 0; index < enemyList.size(); index++) {
                enemyList.get(index).move();
            }
        }

        // Mover viñeta
        if (bullet != null) {
            bullet.setYPosition(bullet.getYPosition() - 15);
            if (bullet.getYPosition() < 0) {
                newBulletCanFire = true;
            }

            // Busca colisiones con enemigos normales
            for (int index = 0; index < enemyList.size(); index++) {
                if (bullet.isColliding(enemyList.get(index))) {
                    AudioPlayer.player.start(hitSoundAudio); // Reproduce el sonido del marcador de golpe si golpeas a un enemigo
                    bullet = new Bala(0, 0, 0, null);
                    newBulletCanFire = true;
                    // Puntuación de actualizaciones para niveles normales
                    if (nivel != 3 && nivel != 6 && nivel != 9 && nivel != 12) {
                        puntaje += 100;
                        hitMarker = true;
                        markerX = enemyList.get(index).getXPosition(); // Obtiene las posiciones que genera el "+ 100" de
                        markerY = enemyList.get(index).getYPosition();
                        enemyList.remove(index);

                    }
                 // Puntuación de actualizaciones para niveles de jefe
                    if (nivel == 3 || nivel == 6 || nivel == 9 || nivel == 12) {
                        hitMarker = true;
                        markerX = enemyList.get(index).getXPosition(); // Obtiene posiciones que el "- 1" genera fuera de
                        markerY = enemyList.get(index).getYPosition() + 165;
                        vidaJefe -= 1;
                        if (vidaJefe == 0) {
                            enemyList.remove(index);
                            puntaje += 9000;// Puntaje de bonificación por derrotar al jefe
                        }
                    }
                }
            }

            // Comprueba colisiones con escudo y balas
            for (int index = 0; index < shieldList.size(); index++) {
                if (bullet.isColliding(shieldList.get(index))) {
                    // Cada instrucción if cambia el color del escudo, indicando "fuerza"
                     // FUERTE
                    if (shieldList.get(index).getColor() == Color.RED) {
                        shieldList.get(index).setColor(Color.ORANGE);
                        AudioPlayer.player.start(shieldSoundAudio); // Reproduce el sonido si el escudo recibe daño
                        bullet = new Bala(0, 0, 0, null);
                        newBulletCanFire = true;
                    // BIEN
                    } else if (shieldList.get(index).getColor() == Color.ORANGE) {
                        shieldList.get(index).setColor(Color.YELLOW);
                        AudioPlayer.player.start(shieldSoundAudio);
                        bullet = new Bala(0, 0, 0, null);
                        newBulletCanFire = true;
                    // OK
                    } else if (shieldList.get(index).getColor() == Color.YELLOW) {
                        shieldList.get(index).setColor(Color.WHITE);
                        AudioPlayer.player.start(shieldSoundAudio);
                        bullet = new Bala(0, 0, 0, null);
                        newBulletCanFire = true;
                    // DÉBIL, ROTURA AL ÉXITO
                    } else if (shieldList.get(index).getColor() == Color.WHITE) {
                        shieldList.remove(index);
                        AudioPlayer.player.start(shieldSoundAudio);
                        bullet = new Bala(0, 0, 0, null);
                        newBulletCanFire = true;
                    }
                }
            }
        }
       // DÉBIL, ROTURA AL ÉXITO
        if (!bonusEnemyList.isEmpty()) {
            for (int index = 0; index < bonusEnemyList.size(); index++) {
                bonusEnemyList.get(index).setXPosition(bonusEnemyList.get(index).getXPosition() + (2));
                if (bonusEnemyList.get(index).getXPosition() > 800) {
                    bonusEnemyList.remove(index);
                    newBonusEnemy = true;
                }
            }
            // enemigo adicional y colisión de bala
            for (int index = 0; index < bonusEnemyList.size(); index++) {
                if (bullet != null) {
                    if (bonusEnemyList.get(index).isColliding(bullet)) {
                        bonusEnemyList.remove(index);
                        bullet = new Bala(0, 0, 0, null);
                        newBulletCanFire = true;
                        newBonusEnemy = true;
                        AudioPlayer.player.start(bonusSoundAudio); // Reproducir sonido si el jugador golpea a un enemigo adicional 
                        puntaje += 5000;  // añadir bonificación para anotar en el golpe
                    }
                }
            }
        }

        // Mueve haces en niveles normales
        if (nivel != 3 && nivel != 6 && nivel != 9 && nivel != 12) {
            if (beam != null) {
                for (int index = 0; index < beamList.size(); index++) {
                    beamList.get(index).setYPosition(beamList.get(index).getYPosition() + (4));
                    if (beamList.get(index).getYPosition() > 800) {
                        beamList.remove(index);
                    }
                }
            }
        }
        // Mueve los haces a una velocidad más rápida para el jefe
        if (nivel == 3 || nivel == 6 || nivel == 9 || nivel == 12) {
            if (beam != null) {
                for (int index = 0; index < beamList.size(); index++) {
                    beamList.get(index).setYPosition(beamList.get(index).getYPosition() + (2 * nivel)); // La velocidad del haz Boss aumentará cada nivel
                    if (beamList.get(index).getYPosition() > 800) {
                        beamList.remove(index);
                    }
                }
            }
        }

        // Verifica las colisiones de la viga y el escudo
        try {
            for (int j = 0; j < shieldList.size(); j++) {
                for (int index = 0; index < beamList.size(); index++) {
                    if (beamList.get(index).isColliding(shieldList.get(j))) {
                        // FUERTE
                        if (shieldList.get(j).getColor() == Color.RED) {
                            shieldList.get(j).setColor(Color.ORANGE);
                            AudioPlayer.player.start(shieldSoundAudio); // Plays sound if shield takes damage
                            beamList.remove(index);
                        // BIEN
                        } else if (shieldList.get(j).getColor() == Color.ORANGE) {
                            shieldList.get(j).setColor(Color.YELLOW);
                            AudioPlayer.player.start(shieldSoundAudio);
                            beamList.remove(index);
                        // OK
                        } else if (shieldList.get(j).getColor() == Color.YELLOW) {
                            shieldList.get(j).setColor(Color.WHITE);
                            AudioPlayer.player.start(shieldSoundAudio);
                            beamList.remove(index);
                        // DÉBIL, ROTURA AL ÉXITO
                        } else if (shieldList.get(j).getColor() == Color.WHITE) {
                            shieldList.remove(j);
                            AudioPlayer.player.start(shieldSoundAudio);
                            beamList.remove(index);
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
        }

        // Verifica las colisiones de la viga y del jugador
        for (int index = 0; index < beamList.size(); index++) {
            if (beamList.get(index).isColliding(nave)) {
                beamList.remove(index);
                AudioPlayer.player.start(damageSoundAudio); // Reproduce el sonido del daño
                lifeList.remove(lifeList.size() - 1); // Elimina vida si es golpeado por bala
            }
        }

        // Hace que los disparos de rayos alcancen solo permitiendo que se disparen nuevos haces una vez que todos los viejos rayos estén fuera de la pantalla o hayan colisionado
        if (beamList.isEmpty()) {
            newBeamCanFire = true;
        }

        // Destruye escudos si los alienígenas chocan con ellos
        for (int input = 0; input < enemyList.size(); input++) {
            for (int j = 0; j < shieldList.size(); j++) {
                if (enemyList.get(input).isColliding(shieldList.get(j))) {
                    shieldList.remove(j);
                }
            }
            // Si los extraterrestres superan esta posición X, restableces el nivel y pierdes una vida
            if (enemyList.get(input).getYPosition() + 50 >= 750) {
                enemyList.clear();
                shieldList.clear();
                lifeList.clear();
                beamList.clear();
                vidaJefe = 30;
                numeroDeVidas -= 1;
                AudioPlayer.player.start(deathSoundAudio); // Reproduce el sonido de la muerte cuando los enemigos llegan al fondo
                setupGame();
            }
        }

        // Actualiza la pantalla del contador de vida
        if (nave.isColliding) {
            int index = lifeList.size() - 1;
            lifeList.remove(index);
        } 
        // Finaliza el juego si el jugador se queda sin vidas
        else if (lifeList.isEmpty()) {
            AudioPlayer.player.start(deathSoundAudio); // Reproduce el sonido de la muerte cuando te quedas sin vidas
             // Le da al jugador una opción para jugar nuevamente o salir
            int answer = JOptionPane.showConfirmDialog(null, "Quiere jugar de nuevo?", "Perdiste con un puntaje de" + puntaje + " puntos", 0);
            // Si eligen jugar nuevamente, esto restablece cada elemento en el juego
            if (answer == 0) {
                lifeList.clear();
                enemyList.clear();
                shieldList.clear();
                beamList.clear();
                bonusEnemyList.clear();
                puntaje = 0;
                nivel = 1;
                vidaJefe = 30;
                numeroDeVidas = 3;
                newBulletCanFire = true;
                newBeamCanFire = true;
                newBonusEnemy = true;
                setupGame();
            }
            // Si eligen no volver a jugar, cierra el juego
            if (answer == 1) {
                System.exit(0);
            }
        }

        // Pasa al siguiente nivel, restablece todas las listas, establece todos los contadores para corregir los valores
        if (enemyList.isEmpty()) {
            beamList.clear();
            shieldList.clear();
            bonusEnemyList.clear();
            lifeList.clear();
            nivel += 1;
            vidaJefe = 30;
            setupGame();
            AudioPlayer.player.start(levelUpSoundAudio); // Reproduce sonido de nivel
        }
        
        // Todas las transmisiones necesarias para cada sonido en el juego
        try {
            beamSoundInput = new FileInputStream(beamSound);
            beamSoundAudio = new AudioStream(beamSoundInput);
            bulletSoundInput = new FileInputStream(bulletSound);
            bulletSoundAudio = new AudioStream(bulletSoundInput);
            levelUpSoundInput = new FileInputStream(levelUpSound);
            levelUpSoundAudio = new AudioStream(levelUpSoundInput);
            deathSoundInput = new FileInputStream(deathSound);
            deathSoundAudio = new AudioStream(deathSoundInput);
            hitSoundInput = new FileInputStream(hitmarkerSound);
            hitSoundAudio = new AudioStream(hitSoundInput);
            shieldSoundInput = new FileInputStream(shieldSound);
            shieldSoundAudio = new AudioStream(shieldSoundInput);
            bossSoundInput = new FileInputStream(bossSound);
            bossSoundAudio = new AudioStream(bossSoundInput);
            bonusSoundInput = new FileInputStream(bonusSound);
            bonusSoundAudio = new AudioStream(bonusSoundInput);
            damageSoundInput = new FileInputStream(damageSound);
            damageSoundAudio = new AudioStream(damageSoundInput);
        } catch (IOException e) {
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// PANEL DE JUEGO   
    
    public PanelJuego() {
        // Establecer el tamaño del panel
        this.setSize(gameWidth, gameHeight);
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(Color.BLACK);

        // Registre el controlador de la placa clave como KeyListener
        controller = new ControladorTeclado();
        this.addKeyListener(controller);

        // Llama a setup Game para inicializar los campos
        this.setupGame();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
     * Método para iniciar el temporizador que controla la animación del juego. Es
      * No es necesario que modifique este código a menos que lo necesite para
      * agregue alguna funcionalidad.
     */
    public void start() {
        // Configurar un nuevo temporizador para repetir cada 20 milisegundos (50 FPS)
        gameTimer = new Timer(1000 / framesPerSecond, new ActionListener() {

            // Registra el número de fotogramas que se han producido.
             // Puede ser útil para limitar las tasas de acción
            private int frameNumber = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Actualizar el estado del juego y volver a pintar la pantalla
                updateGameState(frameNumber++);
                repaint();
            }
        });
        Timer gameTimerHitMarker = new Timer(1000, new ActionListener() {

            // Registra el número de fotogramas que se han producido.
             // Puede ser útil para limitar las tasas de acción
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actualizar el estado del juego y volver a pintar la pantalla
                hitMarker = false;
            }
        });

        gameTimer.setRepeats(true);
        gameTimer.start();
        gameTimerHitMarker.setRepeats(true);
        gameTimerHitMarker.start();
    }

}
