package ttt_ai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.apache.commons.lang3.SerializationUtils;

public class Generation2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -621476338179162409L;
	ArrayList<NN> gen = new ArrayList<NN>();
	Random rnd = new Random();
	int genSize;
	public Generation2(int numNets) {
		genSize = numNets;
		for (int i = 0; i < numNets; i++) {
			gen.add(new NN(18, 9, 2, 5));
		}
	}
	
	public void updateGen(ArrayList<NN> updatedGen) {
		gen = updatedGen;
	}
	
	public void nextGen(ArrayList<NN> bestNN) {
		gen.clear();
		for (NN nn : bestNN) {
			nn.turnAverage = 0;
			nn.turnCounts.clear();
			nn.winCount = 0;
			gen.add(nn);
		}
		
		for (int i = 0; i < 100; i++) {
			bestNN.add(bestNN.get(0));
		}
		for (int i = 0; i < 50; i++) {
			bestNN.add(bestNN.get(1));
		}
		for (int i = 0; i < 25; i++) {
			bestNN.add(bestNN.get(2));
		}
		for (int i = 0; i < 10; i++) {
			bestNN.add(bestNN.get(3));
		}
		
		//System.out.println(bestNN.size());
		for (int i = 0; i < genSize - 14; i++) {
			NN net = SerializationUtils.clone(bestNN.get(rnd.nextInt(189)));
			net.turnAverage = 0;
			net.turnCounts.clear();
			net.winCount = 0;
			net.adjustWeights(5);
			gen.add(net);
		}
		for (int i = 0; i < 10; i++) {
			gen.add(new NN(18, 9, 2, 5));
		}
	}
	
	public NN testBest(NN bestOg) {
		NN best = SerializationUtils.clone(bestOg);
		//System.out.println("Played");
		best.turnAverage = 0;
		best.winCount = 0;
		NN antiPlayer = new standardPlayer(27, 9, 3, 5);
		for (int i = 0; i < 200; i++) {
			//System.out.println("Here");
			Game game = new Game(best, antiPlayer);
			game.playGame();
			
		}
		return best;
	}
	
	public NN getBest() {
		Collections.sort(gen);
		return gen.get(0);
	}
	
	public NN getSecondBest() {
		Collections.sort(gen);
		return gen.get(1);
	}
	
	public NN getThirdBest() {
		Collections.sort(gen);
		return gen.get(2);
	}
	
	public NN getFourthBest() {
		Collections.sort(gen);
		return gen.get(3);
	}
	
	

	public ArrayList<NN> getGen() {
		Collections.sort(gen);
		return gen;
	}

	@Override
	public String toString() {
		return "Generation [" + gen + "]";
	}
	
	
}
