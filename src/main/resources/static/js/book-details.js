let path = new URL(window.location.href).pathname;
let split = path.split("/");
let roomId = parseInt(split[split.length - 1]);
console.log(roomId);

let priceElement = document.querySelector("#book-details__price");
let price = parseInt(priceElement.innerHTML.slice(5));
let priceFormat = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
priceElement.innerHTML = `Giá: ${priceFormat}`;

let btnElement = document.querySelector("#book-details__btn");
btnElement.onclick = () => {
    window.location.href = `/dat-phong/${roomId}`;
}