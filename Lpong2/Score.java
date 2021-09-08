//parte gráfica
import java.awt.*;
import java.awt.image.*;

//erros
import java.io.*;
import javax.imageio.*;

/**
	Esta classe representa um placar no jogo. A classe princial do jogo (Pong)
	instancia dois objeto deste tipo, cada um responsável por gerenciar a pontuação
	de um player, quando a execução é iniciada.
*/

public class Score {

	// variáveis utilizadas na mecânica do score
	private String playerId;
	private int placar;
	private double alpha;
	private Color lightyellow = new Color(255, 255, 20); //cor do score dos players
	//------------------------------

	// variáveis das imagens
	BufferedImage pongtexto = null;
	BufferedImage parede = null;
	//------------------------------


	public Score(String playerId){
		this.playerId = playerId;
		placar = 0;

		try { //pega a imagem do texto PONG
				pongtexto = ImageIO.read(getClass().getResource("imagens/PONG.png"));
		} catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}

		try { //pega a imagem da parede
				parede = ImageIO.read(getClass().getResource("imagens/parede20x20.png"));
		} catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}


	public void draw(){

		String placarString = Integer.toString(placar);

		//desenho das paredes
		for (int i = 0; i < 800; i = i+20){ //desenho do fundo do score //o +10 é para que não sobre nenhum espaço
			GameLib.g.drawImage(parede, i, 100, null);
		}
		for (int i = 0; i < 800; i = i+20){ //desenho do fundo do score //o +10 é para que não sobre nenhum espaço
			GameLib.g.drawImage(parede, i, 580, null);
		}
		for (int i = 0; i < 600; i = i+20){ //desenho do fundo do score //o +10 é para que não sobre nenhum espaço
			GameLib.g.drawImage(parede, 0, i, null);
		}
		for (int i = 0; i < 600; i = i+20){ //desenho do fundo do score //o +10 é para que não sobre nenhum espaço
			GameLib.g.drawImage(parede, 780, i, null);
		}
		//--------------------------------

		GameLib.g.drawImage(pongtexto, (GameLib.WIDTH/2)-100, 30, null); //desenho PONG

		//desenho do score dos players
		if (playerId.equals(Pong.PLAYER1)){
			GameLib.setColor(lightyellow);
			GameLib.drawText(playerId + ": " + placarString, 70, GameLib.ALIGN_LEFT);
		}else{
			GameLib.setColor(lightyellow);
			GameLib.drawText(playerId + ": " + placarString, 70, GameLib.ALIGN_RIGHT);
		}

	}


	public void inc(){

		placar = placar + 1;

	}


	public int getScore(){

		return placar;
	}
}
