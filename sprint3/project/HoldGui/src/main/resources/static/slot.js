

const slotsData = [
      { id: "", slotID: 1, peso: 0, occupato: false },
      { id: "", slotID: 2, peso: 0, occupato: false },
      { id: "", slotID: 3, peso: 0, occupato: false },
      { id: "", slotID: 4, peso: 0, occupato: false }
    ];

    function renderSlots() {
      const container = document.getElementById("slots");
      container.innerHTML = "";

      slotsData.forEach((slot, index) => {
        const div = document.createElement("div");
        div.className = `slot ${slot.occupato ? "occupied" : "free"}`;

        div.innerHTML = `
          <strong>SlotID:</strong> ${slot.slotID}<br>
          <strong>ID:</strong> ${slot.id}<br>
          <strong>Peso:</strong> ${slot.peso}<br></label>
          <strong>Occupato:</strong> ${slot.occupato ? "Si" : "No"}
        `;

        container.appendChild(div);
      });
    }

    function updateSlot(index, ProductId, peso) {
      slotsData[index].id=ProductId
      slotsData[index].peso=peso
      slotsData[index].occupato=true
      renderSlots();
    }

document.addEventListener("DOMContentLoaded", () => {
  renderSlots();
});



  