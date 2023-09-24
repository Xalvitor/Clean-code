import java.util.*;

public class SistemaDePedidos{

    public static void main(String[] args) {

        List<Pedido> listaPedidos = criarListaDePedidos();

        //Mostra as opções do menu e é responsavel por aceitar entradas do usuário.
        criaMenu(listaPedidos);

    }

    public static List<Pedido> criarListaDePedidos(){

        return new ArrayList<>();

    }

    public static void criaMenu(List<Pedido> listaPedidos){

        Scanner selecaoDoUsuario = new Scanner(System.in);

        //Mapeanmento do menu de opções.
        Map<Integer, Runnable> opcoesMenu = new HashMap<>();
        opcoesMenu.put(1, () -> criarPedido(listaPedidos));
        opcoesMenu.put(2, () -> adicionarItemAPedido(listaPedidos, selecaoDoUsuario));
        opcoesMenu.put(3, () -> calcularTotalPedido(listaPedidos));
        opcoesMenu.put(4, () -> listarItensPedido(listaPedidos));
        opcoesMenu.put(5, () -> {
            selecaoDoUsuario.close();
            System.exit(0);
        });

        while (true) {

            //Responsavel por exibir as opções do menu.
            eixibirOpcoesDoMenu();

            //Tratamento de erros caso usuário coloque uma entrada inválida.
            try{

                int opcaoEscolhida = selecaoDoUsuario.nextInt();

                //Busca a opção selecionada no mapa.
                Runnable opcaoSelecionada = opcoesMenu.get(opcaoEscolhida);

                if (opcaoSelecionada != null){

                    opcaoSelecionada.run();

                } else {

                    System.out.println("Entrada inválida, escolha uma das opções seguintes:");

                }

            } catch (InputMismatchException entradaInvalida) {

                //Em caso de entrada em formato inválido, pede para usar números.
                System.out.println("Entrada em formato inválido, se certifique de usar números");

                selecaoDoUsuario.nextLine();

            }

        }

    }

    public static void eixibirOpcoesDoMenu(){

        //Responsavel por exibir as possiveis opções do menu
        System.out.println("1. Criar Pedido");

        System.out.println("2. Adicionar Item ao Pedido");

        System.out.println("3. Calcular Total do Pedido");

        System.out.println("4. Listar Itens do Pedido");

        System.out.println("5. Sair");

        System.out.print("Escolha uma opção: ");

    }

    public static void criarPedido(List<Pedido> listaPedidos){

        //Cria um pedido novo.
        Pedido novoPedido = new Pedido();

        listaPedidos.add(novoPedido);

        System.out.println("Pedido criado.");

    }

    public static void adicionarItemAPedido(List<Pedido> listaPedidos, Scanner selecaoDoUsuario){

        if (listaPedidos.isEmpty()) {

            System.out.println("Crie um pedido primeiro.");

            return;

        }

        System.out.print("Digite o nome do item: ");

        String nomeItem = selecaoDoUsuario.next();

        System.out.print("Digite o preço do item: ");

        double precoItem = selecaoDoUsuario.nextDouble();

        //Verificação se o valor do produto é menor ou igual a zero.
        while (precoItem < 0 || precoItem == 0){

            System.out.println("O preco do produto precisa ser positivo.");

            precoItem = selecaoDoUsuario.nextDouble();

        }

        Item novoItem = new Item(nomeItem, precoItem);

        //Pega o ultimo pedido gerado.
        Pedido pedidoAtual = retornarPedidoAtual(listaPedidos);

        pedidoAtual.adicionarItem(novoItem);

        System.out.println("Item adicionado ao pedido.");

    }

    public static void calcularTotalPedido(List<Pedido> listaPedidos){

        if (listaPedidos.isEmpty()) {

            System.out.println("Crie um pedido primeiro.");

            return;

        }

        //Pega o ultimo pedido gerado.
        Pedido pedidoAtual = retornarPedidoAtual(listaPedidos);

        //Verifica se tem algum item no pedido atual.
        if (pedidoAtual.getItens().isEmpty()) {

            System.out.println("Adicione um item primeiro.");

            return;

        }

        double precoTotal = pedidoAtual.calcularTotal();

        System.out.println("Total do pedido: " + precoTotal);

    }

    public static void listarItensPedido(List<Pedido> listaPedidos){

        if (listaPedidos.isEmpty()) {

            System.out.println("Crie um pedido primeiro.");

            return;

        }

        //Pega o ultimo pedido gerado.
        Pedido pedidoParaListar = retornarPedidoAtual(listaPedidos);

        //Pega os itens do pedido
        List<Item> itensDoPedido = pedidoParaListar.getItens();

        if (itensDoPedido.isEmpty()){

            System.out.println("Adicione um item primeiro.");

            return;

        }

        System.out.println("Itens do pedido:");

        //Lista o nome e preço de cada item do pedido.
        for (Item item : itensDoPedido) {

            System.out.println(item.getNome() + ": " + item.getPreco());

        }

    }
    public static Pedido retornarPedidoAtual (List<Pedido> listaPedidos){

        return listaPedidos.get(listaPedidos.size() - 1);

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

        double precoTotal = 0;

        for (Item item : listaItens) {

            precoTotal += item.getPreco();

        }

        return precoTotal;

    }

    public List<Item> getItens() {

        return listaItens;

    }

}
