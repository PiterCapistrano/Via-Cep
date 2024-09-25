package com.pitercapistrano.viacep

// Importa pacotes necessários para manipulação de cores, UI, atividades e chamadas de API
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pitercapistrano.viacep.api.Api
import com.pitercapistrano.viacep.databinding.ActivityMainBinding
import com.pitercapistrano.viacep.model.Endereco
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

// Define a classe principal da atividade
class MainActivity : AppCompatActivity() {

    // Declaração da variável binding para acessar os elementos da UI
    private lateinit var binding: ActivityMainBinding

    // Função chamada quando a atividade é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita o modo Edge-to-Edge, permitindo a atividade usar toda a tela
        enableEdgeToEdge()

        // Inicializa o binding, conectando com o layout XML (ActivityMain)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Define a visualização raiz como o conteúdo da tela

        // Ajusta o layout para se adaptar à presença de barras de status ou navegação no sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()) // Obtém as margens das barras do sistema
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom) // Ajusta o padding do layout
            insets
        }

        // Define a cor da barra de status
        window.statusBarColor = Color.parseColor("#ff018786")

        // Configuração do Retrofit, ferramenta para realizar chamadas de API
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) // Usa o Gson para converter respostas JSON
            .baseUrl("https://viacep.com.br/") // Define a URL base da API (ViaCEP)
            .build()
            .create(Api::class.java) // Cria a implementação da interface da API

        // Define o comportamento ao clicar no botão "Buscar"
        binding.buscar.setOnClickListener {
            val cep = binding.cep.text.toString() // Obtém o valor digitado no campo de CEP

            // Verifica se o campo de CEP está vazio
            if (cep.isEmpty()) {
                // Exibe uma mensagem pedindo para preencher o campo de CEP
                Toast.makeText(this, "Preencha o Cep!", Toast.LENGTH_SHORT).show()
            } else {
                // Faz a chamada à API ViaCEP com o valor do CEP fornecido
                retrofit.setEndereco(cep).enqueue(object : Callback<Endereco> {

                    // Função executada se a resposta for bem-sucedida
                    override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                        // Verifica se o código de resposta HTTP é 200 (OK)
                        if (response.code() == 200) {
                            // Extrai os dados do endereço da resposta e os converte em Strings
                            val logradouro = response.body()?.logradouro.toString()
                            val bairro = response.body()?.bairro.toString()
                            val localidade = response.body()?.localidade.toString()
                            val uf = response.body()?.uf.toString()

                            // Atualiza os campos da UI com os dados recebidos
                            setFormularios(logradouro, bairro, localidade, uf)
                        } else {
                            // Exibe uma mensagem caso o CEP seja inválido
                            Toast.makeText(applicationContext, "Cep inválido!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    // Função executada em caso de falha na chamada da API
                    override fun onFailure(call: Call<Endereco>, t: Throwable) {
                        // Exibe uma mensagem em caso de erro inesperado
                        Toast.makeText(applicationContext, "Erro inesperado!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    // Função que preenche os campos da tela com os dados recebidos da API
    private fun setFormularios(logradouro: String, bairro: String, localidade: String, uf: String) {
        binding.rua.setText(logradouro) // Preenche o campo "Rua" com o valor do logradouro
        binding.bairro.setText(bairro) // Preenche o campo "Bairro"
        binding.cidade.setText(localidade) // Preenche o campo "Cidade"
        binding.estado.setText(uf) // Preenche o campo "Estado"
    }
}
