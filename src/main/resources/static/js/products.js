async function fetchProducts(date) {
    const response = await fetch(`products?date=${date}`);

    if (!response.ok) {
        const errorText = await response.text();
        document.getElementById("poruka").textContent = errorText;
        return;
    }

    document.getElementById("poruka").textContent = "";
    const data = await response.json();
    renderProducts(data);
}

function renderProducts(data) {
    const tbody = document.getElementById("products-body");
    tbody.innerHTML = "";

    data.forEach(product => {
        const row = document.createElement("tr");

        const nameCell = document.createElement("td");
        nameCell.textContent = product.name;

        const priceCell = document.createElement("td");
        priceCell.textContent = product.price;

        const unitCell = document.createElement("td");
        unitCell.textContent = product.unit;

        const gradeCell = document.createElement("td");
        gradeCell.textContent = product.grade;

        row.append(nameCell, priceCell, unitCell, gradeCell);
        tbody.appendChild(row);
    });
}

async function fetchSum(date) {
    const spinner = document.getElementById("spinner");
    const resultSpan = document.getElementById("sum-result");

    spinner.classList.remove("d-none");
    resultSpan.textContent = "";

    const response = await fetch(`products/sum?date=${date}`);

    spinner.classList.add("d-none");

    if (!response.ok) {
        const errorText = await response.text();
        document.getElementById("poruka").textContent = errorText;
        return;
    }

    document.getElementById("poruka").textContent = "";
    const sum = await response.json();
    resultSpan.textContent = `Sum of prices: ${sum}`;
}

document.getElementById("fetch-btn").addEventListener("click", () => {
    const date = document.getElementById("date-input").value;
    fetchProducts(date);
});

document.getElementById("sum-btn").addEventListener("click", () => {
    const date = document.getElementById("date-input").value;
    fetchSum(date);
});