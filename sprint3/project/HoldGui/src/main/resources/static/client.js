
const ws= new WebSocket("ws://localhost:8080/clientws");



function onRegisterClick (event){
    event.preventDefault();

        const outputDiv = document.getElementById("result");
        const errorDiv = document.getElementById("registerError");

        errorDiv.textContent = "";

            const pid = document.getElementById("pid").value.trim();
            const peso = parseInt(document.getElementById("peso").value);
            const nome = document.getElementById("nome").value.trim();

             if (isNaN(peso) || peso <= 0) {
                errorDiv.textContent = "Il peso deve essere un numero positivo.";
                return;
            }

            
            let RequestObj= {};
            RequestObj.Type="Register";
            RequestObj.PID=pid;
            RequestObj.Nome=nome;
            RequestObj.Peso=peso;

            ws.send(JSON.stringify(RequestObj));
            outputDiv.innerHTML += `<p>Prodotto registrato: PID=${pid}, Peso=${peso}, Nome=${nome}</p>`;

}
       
       
function onLoadClick(event){
    
    const outputDiv = document.getElementById("result");

         event.preventDefault();

        const pid = document.getElementById("pidLoad").value.trim();
        let RequestObj= {};
        RequestObj.Type="Request";
        RequestObj.PID=pid;
        

        ws.send(JSON.stringify(RequestObj));
        outputDiv.innerHTML += `<p>Richiesta di carico per PID=${pid}</p>`;

}
