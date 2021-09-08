//parte gráfica
import java.awt.*;
import java.awt.image.*;

//gerar número random
import java.util.*;

//som
import java.net.URL;
import javax.sound.sampled.*;

//erros
import java.io.*;
import javax.imageio.*;

/**
	Esta classe representa a bola usada no jogo. A classe princial do jogo (Pong)
	instancia um objeto deste tipo quando a execução é iniciada.
*/

public class Ball {

	// variáveis utilizadas na mecânica da bola
	private double cx;
	private double cy;
	private double width;
	private double height;
	private Color color;
	private double speedball;
	private double basespeedball;
	public static double speedplayer = 0.5; //aqui está hardcoded pelo fato de ser necessário que seja static, pois quem muda a velocidade do player é a classe ball.
	private double basespeedplayer = 0.5; //mesmo motivo da linha de cima
	private int directionX; //1 é para a direita, 2 é para a esquerda;
	private int directionY; //1 é para cima, 2 é para baixo;
	//------------------------------------------

	//variáveis do som
	private Clip clip;
	private URL url;
	//------------------------------------------

	//variáveis das imagens
	private BufferedImage fundomapa = null;
	private BufferedImage imgbola = null;
	private BufferedImage fundo = null;
	//------------------------------------------



	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.color = color;

		speedball = speed; //mecânica de acelerar a bola a cada frame
		basespeedball = speed; //mecânica de acelerar a bola a cada frame

		Random gerador = new Random();//gera direções aleatórias do começo da bola podendo ser 1 ou 2
		directionX = (gerador.nextInt(2))+1;
		directionY = (gerador.nextInt(2))+1;

		try { //pega a imagem do fundo do mapa
				fundomapa = ImageIO.read(getClass().getResource("imagens/fundoladescuro.png"));
		} catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}

		try { //pega a imagem da bola
				imgbola = ImageIO.read(getClass().getResource("imagens/ball.png"));
		} catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}

		try { //pega a imagem do fundo branco
				fundo = ImageIO.read(getClass().getResource("imagens/fundoverdelata.png"));
		} catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}

		url = getClass().getResource("sons/click.wav");//som da bola batendo nas coisas
    SoundEffect(url);//carrega e cria o clip do áudio

	}


	public void draw(){

		for (int i = 0; i < 800; i = i+96){ //desenho do fundo da "arena"
			for (int j = 0; j < 600; j = j +96){
				GameLib.g.drawImage(fundomapa, i, j, null);
			}
		}

		GameLib.g.drawImage(imgbola, (int)Math.round(cx) - (int)Math.round(width/2), (int)Math.round(cy) - (int)Math.round(height/2) , null); //desenha a bola

		for (int i = 0; i < GameLib.WIDTH; i = i+128){ //desenho do fundo do score
			GameLib.g.drawImage(fundo, i, 0, null);
		}

	}


	public void update(long delta){

		if (directionY == 1){//se está andando para cima
			cy = cy - (delta*speedball);
			if(directionX == 1){//se está andando para a direita
				cx = cx + (delta*speedball);
			}else{//se está andando para a esquerda
				cx = cx - (delta*speedball);
			}
		}else{//se está andando para baixo
			cy = cy + (delta*speedball);
			if(directionX == 1){//se está andando para a direita
				cx = cx + (delta*speedball);
			}else{//se está andando para a esquerda
				cx = cx - (delta*speedball);
			}
		}
		speedball = speedball*1.001; //aumenta as velocidades exponencialmente
		speedplayer = speedplayer*1.001;

	}


	public void onPlayerCollision(String playerId){

		if(playerId.equals(Pong.PLAYER1) && directionX != 1){//se bateu no player 1, muda a direção da bola para a direita
			directionX = 1;
			playTheSound(url); //roda o som
		}
		if(playerId.equals(Pong.PLAYER2) && directionX != 2){//se bateu no player 2, muda a direção da bola para a esquerda
			directionX = 2;
			playTheSound(url); //roda o som
		}

	}



	public void onWallCollision(String wallId){

		playTheSound(url); //roda o som

		if(wallId.equals(Pong.TOP)){
			directionY = 2;
		}
		if(wallId.equals(Pong.RIGHT)){
			directionX = 2;
			speedball = basespeedball;
			speedplayer = basespeedplayer; //reseta as velocidades dos players, pois marcou ponto
		}
		if(wallId.equals(Pong.LEFT)){
			directionX = 1;
			speedball = basespeedball;
			speedplayer = basespeedplayer; //reseta as velocidades dos players, pois marcou ponto
		}
		if(wallId.equals(Pong.BOTTOM)){
			directionY = 1;
		}
	}



	public boolean checkCollision(Wall wall){

		if(wall.getId().equals(Pong.TOP) && cy < wall.getCy() + wall.getHeight()){
			return true;
		}
		if(wall.getId().equals(Pong.RIGHT) && cx > wall.getCx() - wall.getWidth()){
			if (cx > wall.getCx() - wall.getWidth()){ //arruma o bug de marcar 2 ou mais pontos quando a bola está muito rápida (a quantidade de pontos depende da quantidade de "ticks" do método checkcollision, e caso a bola esteja muito rápida, acontecerá mais do q um "tick" com a bola dentro da parede)
				cx = (wall.getCx() - wall.getWidth()) - 1;
			}
			return true;
		}
		if(wall.getId().equals(Pong.LEFT) && cx < wall.getCx() + wall.getWidth()){
			if(cx < wall.getCx() + wall.getWidth()){ //arruma o bug de marcar 2 ou mais pontos quando a bola está muito rápida (a quantidade de pontos depende da quantidade de "ticks" do método checkcollision, e caso a bola esteja muito rápida, acontecerá mais do q um "tick" com a bola dentro da parede)
				cx = (wall.getCx() + wall.getWidth()) + 1;
			}
			return true;
		}
		if(wall.getId().equals(Pong.BOTTOM) && cy > wall.getCy() - wall.getHeight()){
			return true;
		}

		return false;
	}


	public boolean checkCollision(Player player){

		//se a bola estiver dentro da área do player recebido como parâmetro, ele retornará true. O teste do id é para que não entre duas vezes o código (há uma execução para cada player), o que resultaria em problemas

		if(player.getId().equals(Pong.PLAYER1) && cx - (width/2) < player.getCx() + (player.getWidth()/2) && cx + (width/2) > player.getCx() - (player.getWidth()/2) && cy < player.getCy()+(player.getHeight()/2) && cy > player.getCy()-(player.getHeight()/2)){
			return true;
		}
		if(player.getId().equals(Pong.PLAYER2) && cx - (width/2) < player.getCx() + (player.getWidth()/2) && cx + (width/2)> player.getCx() - (player.getWidth()/2) && cy < player.getCy()+(player.getHeight()/2) && cy > player.getCy()-(player.getHeight()/2)){
			return true;
		}

		return false;
	}


	public double getCx(){

		return cx;
	}


	public double getCy(){

		return cy;
	}


	public double getSpeed(){

		return speedball;
	}

	private void SoundEffect(URL url) {
    try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
				clip.start();
    } catch (UnsupportedAudioFileException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (LineUnavailableException e) {
        e.printStackTrace();
    }
	}

	public void playTheSound(URL url) {

    clip.setFramePosition(700); // rewind to the beginning
    clip.start();     // Start playing

  }




}
