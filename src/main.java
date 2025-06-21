import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class main {
    public static void main(String[] args) {
        int length= 0, width = 0, resultado;
        char[][] map = {};
        String directoryPath = "C:/Users/pedro/Documents/DalestT2/CodigoFonte/casosDeTeste";
        try{
        BufferedReader reader = new BufferedReader(new FileReader(directoryPath + "/case0.map"));
        String[] dadosString = reader.readLine().split(" ");
        length = Integer.parseInt(dadosString[0]);
        width = Integer.parseInt(dadosString[1]);
        map = new char[length][width];
        System.out.println(length + " "+ width);
        for (int i = 0; i < length; i++) {
            map [i]=reader.readLine().toCharArray();
        }
        reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        for (int i = 0; i < length; i++){
            for (int j = 0; j < width; j++){
                if (map[i][j] == '1'){
                    resultado = BreadthFirstSearch(map, i, j);
                    System.out.println("O total gasto é " + resultado + " unidade de combustível");
                    return;
                }
            }
        }

    }
    private static final int[][] DIRECTION = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };
    private static int BreadthFirstSearch(char[][] map, int n1 , int n2){
        int resultado = 0, anterior = 1;
        boolean flag = false, last = false;
        int[] from = {n1,n2};
        int [][] values = new int[map.length][map[0].length];

        for (int destination = 2; !last; destination++) {
            if (destination == 10) {
                destination = 1;
                last = true;
            }
            Queue<int[]> queue = new LinkedList<>();
            queue.add(from);
            for (int a = 0 ; a < map.length ; a++) {
                Arrays.fill(values[a], Integer.MAX_VALUE);
            }
            values[from[0]][from[1]] = 0;
            while (!queue.isEmpty()) {
                int[] temporario = queue.poll();
                for (int[] position : DIRECTION) {
                    n1 = temporario[0] + position[0];
                    n2 = temporario[1] + position[1];
                    if (n2 >= map[0].length || n1 >= map.length || n2 < 0 || n1 < 0) {
                        continue;
                    }
                    char atual = map[n1][n2];
                    if (atual == (char) ('0' + destination)) {
                        resultado += values[temporario[0]][temporario[1]] + 1;
                        System.out.println(" O valor para chegar de " + anterior + " até " + destination + " sendo essa jornada o valor de " + (values[temporario[0]][temporario[1]] + 1) +  "\n O total até agora é " + resultado);
                        queue.add(new int[]{n1, n2});
                        flag = true;
                        break;
                    } else if (atual == '*') {
                        continue;
                    } else {
                        if (atual != '.') {
                            continue;
                        }
                        else if (values[temporario[0]][temporario[1]] + 1 < values[n1][n2]) {
                            values[n1][n2] = values[temporario[0]][temporario[1]] + 1;
                            queue.add(new int[]{n1, n2});
                        }
                    }
                }
                if (flag){
                    break;
                }
            }
            if (flag){
                anterior = destination;
                from = new int[]{n1,n2};
                flag = false;
            }
            else {
                System.out.println(" O porto " + destination + " não é alcançável");
            }
        }

        return resultado;
    }
}