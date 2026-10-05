import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    // REQUISITO: Funcionalidade Salvar (Cadastrar) no Banco de Dados
    public boolean cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB(); // Abre a conexão com o banco
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate(); // Executa a inserção
            return true; // Retorna verdadeiro se salvou com sucesso

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar no banco: " + erro.getMessage());
            return false; // Retorna falso se deu erro
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // REQUISITO: Listagem de dados vindos do banco de dados
    public ArrayList<ProdutosDTO> listarProdutos() {
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos";
        listagem.clear(); // Limpa a lista antes de preencher

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id")); // Verifique se o campo no banco chama 'id'
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return listagem;
    }
}
