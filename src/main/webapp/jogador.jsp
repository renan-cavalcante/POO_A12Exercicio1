<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Jogador</title>
</head>
<body>

	<h1>Jogador:</h1>

	<form action="jogador" method="post">
		<label>Nome:</label> <input name="nome"> <br> <label>Data
			de nascimento</label> <input name="dataNasc"> <br> <label>Altura</label>
		<input name="altura"> <br> <label>Pesoo</label> <input
			name="peso"> <br> <select id="timeSection" name="time">
		</select> <br>


		<button type="submit" name="button" value="Insert">Inserir</button>
		<button type="submit" name="button" value="Delete">Deletar</button>
		<button type="submit" name="button" value="Update">Atualizar</button>
		<button type="submit" name="button" value="List">Listar</button>
		<button type="submit" name="button" value="Find">Encontrar</button>
	</form>



	<script>
	function carregarTimes() {
        fetch('http://localhost:8080/POO_A12Exercicio01/time', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body:'button=List'
        })
        .then(response => response.json())
        .then(data => {
            const timesSection = document.getElementById('timesSection');
            timesSection.innerHTML = ''; 
            data.times.forEach(time => {
                const timeOption = document.createElement('option');
                timeOption.textContent = time.nome;
                timeOption.value = time.codigo;
                timesSection.appendChild(timeOption);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar os times:', error);
        });
    }

    carregarTimes();

       
    </script>
</body>
</html>