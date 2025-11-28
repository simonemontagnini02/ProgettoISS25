
const ws= new WebSocket("ws://localhost:8080/clientws");

ws.onmessage= (event)=>{

    let data = JSON.parse(event.data);
    const outputDiv = document.getElementById("result");
    const peso = parseInt(document.getElementById("peso").value);
    const nome = document.getElementById("nome").value.trim();
    const pid = document.getElementById("pid").value.trim();

    if (data.Type==="Register") {

        if (data.PID==="0") {

            
            outputDiv.innerHTML += `<p>Registrazione del prodotto ${pid} non riuscita</p>`;
        }
        else{

            outputDiv.innerHTML += `<p>Prodotto registrato: PID=${pid}, Peso=${peso}, Nome=${nome}</p>`;

        }
        
    }
    else{

        const pid = document.getElementById("pidLoad").value.trim();
        const slot=data.Slot;

        if(data.Accepted==="ok"){

            outputDiv.innerHTML += `<p>Prodotto con PID=${pid} caricato nello slot ${slot}</p>`;

        }
        else{

            switch (slot) {
                case "PID_NOT_REGISTERED":

                    outputDiv.innerHTML += `<p>Impossibile caricare il prodotto con PID=${pid}, il PID non è registrato </p>`;

                    break;

                case "MAX_LOAD_EXCEEDED":

                    outputDiv.innerHTML += `<p>Impossibile caricare il prodotto con PID=${pid}, il peso supera la capacità della stiva</p>`;

                    break

                case "NO_FREE_SLOTS":

                    outputDiv.innerHTML += `<p>Impossibile caricare il prodotto con PID=${pid}, non ci sono slot disponibili</p>`;

                    break

                default:
                    outputDiv.innerHTML += `<p>Risposta errata</p>`;
                    break;
            }


        }


    }
    


}


function clearOutput(event) {

    document.getElementById("result").innerHTML="";
    
}


function onRegisterClick (event){
    event.preventDefault();

        const outputDiv = document.getElementById("result");
        const errorDiv = document.getElementById("registerError");

        errorDiv.textContent = "";

            const pid = Number(document.getElementById("pid").value.trim());
            const peso = Number(document.getElementById("peso").value.trim());
            const nome = document.getElementById("nome").value.trim();
			
			if (isNaN(pid) || pid <= 0) {
			                errorDiv.textContent = "Il PID deve essere un numero positivo.";
			                return;
			            }

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
