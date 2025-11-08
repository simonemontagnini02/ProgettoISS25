
let ws=new WebSocket("ws://localhost:8080/ws")

function onmsgCallback(event) {
    
    console.log(event.data)
    let jsonObj=JSON.parse(event.data)
    updateSlot(jsonObj.slotID, jsonObj.id, jsonObj.peso)

}


ws.onmessage = onmsgCallback
ws.onmessage