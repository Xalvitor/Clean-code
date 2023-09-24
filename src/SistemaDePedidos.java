import java.util.*;

public class SistemaDePedidos{

    public static void main(String[] args) {

        List<Pedido> listaPedidos = criarListaDePedidos();

        executarMenu(listaPedidos);

    }

    public static List<Pedido> criarListaDePedidos(){

        return new ArrayList<>();

    }

    public static void executarMenu(List<Pedido> listaPedidos){

        Scanner selecaoDoUsuario = new Scanner(System.in);

        //Mapeando as opções do menu de opções
        Map<Integer, Runnable> opcoesMenu = new HashMap<>();
        opcoesMenu.put(1, () -> criarNovoPedido(listaPedidos));
        opcoesMenu.put(2, () -> adicionarItemAPedido(listaPedidos));
        opcoesMenu.put(3, () -> calcularTotalPedido(listaPedidos));
        opcoesMenu.put(4, () -> listarItensPedido(listaPedidos));
        opcoesMenu.put(5, () -> {
            selecaoDoUsuario.close();
            System.exit(0);
        });

        while (true) {

            eixibirOpcoes();

            try{

                int opcaoEscolhida = selecaoDoUsuario.nextInt();

                Runnable opcaoSelecionada = opcoesMenu.get(opcaoEscolhida);

                if (opcaoSelecionada != null){
                    opcaoSelecionada.run();
                } else {
                    System.out.println("Entrada inválida, escolha uma das opções seguintes:");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada em formato inválido, certifiquese de usar números");
                selecaoDoUsuario.nextLine();
            }

        }

    }

    public static void eixibirOpcoes(){

        System.out.println("1. Criar Pedido");

        System.out.println("2. Adicionar Item ao Pedido");

        System.out.println("3. Calcular Total do Pedido");

        System.out.println("4. Listar Itens do Pedido");

        System.out.println("5. Sair");

        System.out.print("Escolha uma opção: ");

    }

    public static void criarNovoPedido(List<Pedido> listaPedidos){

        Pedido novoPedido = new Pedido();

        listaPedidos.add(novoPedido);

        System.out.println("Pedido criado.");

    }

    public static void adicionarItemAPedido(List<Pedido> listaPedidos){

        if (listaPedidos.isEmpty()) {

            System.out.println("Crie um pedido primeiro.");

            return;

        }
        Scanner inserirAtributoItem = new Scanner(System.in);

        System.out.print("Digite o nome do item: ");

        String nomeItem = inserirAtributoItem.next();

        System.out.print("Digite o preço do item: ");

        double precoItem = inserirAtributoItem.nextDouble();

        Item novoItem = new Item(nomeItem, precoItem);

        Pedido ultimoPedido = listaPedidos.get(listaPedidos.size() - 1);

        ultimoPedido.adicionarItem(novoItem);

        System.out.println("Item adicionado ao pedido.");

        inserirAtributoItem.close();

    }

    public static void calcularTotalPedido(List<Pedido> listaPedidos){

        if (listaPedidos.isEmpty()) {

            System.out.println("Crie um pedido primeiro.");

            return;

        }

        Pedido pedidoAtual = listaPedidos.get(listaPedidos.size() - 1);

        if (pedidoAtual.calcularTotal() == 0) {

            System.out.println("Adicione um item primeiro.");

            return;

        }

        double total = pedidoAtual.calcularTotal();

        System.out.println("Total do pedido: " + total);

    }

    public static void listarItensPedido(List<Pedido> listaPedidos){

        if (listaPedidos.isEmpty()) {

            System.out.println("Crie um pedido primeiro.");

            return;


        }

        Pedido pedidoParaListar = listaPedidos.get(listaPedidos.size() - 1);

        List<Item> itensDoPedido = pedidoParaListar.getItens();

        System.out.println("Itens do pedido:");

        for (Item i : itensDoPedido) {
            System.out.println(i.getNome() + ": " + i.getPreco());
        }

    }
}

class Item {

    private String nome;

    private double preco;

    public Item(String nome, double preco) {

        this.nome = nome;

        this.preco = preco;

    }

    public String getNome() {

        return nome;

    }

    public double getPreco() {

        return preco;

    }

}

class Pedido {

    private List<Item> listaItens = new ArrayList<>();

    public void adicionarItem(Item novoItem) {

        listaItens.add(novoItem);

    }

    public double calcularTotal() {

        double total = 0;

        for (Item item : listaItens) {

            total += item.getPreco();

        }

        return total;

    }

    public List<Item> getItens() {

        return listaItens;

    }
}
