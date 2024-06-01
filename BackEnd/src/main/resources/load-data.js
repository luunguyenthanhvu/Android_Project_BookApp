'use strict';
function generateRandomInteger(N) {
  return Math.floor(Math.random() * N) + 1;
}
function generateRandomFloat(N) {
  return (Math.random() * N + 1.0).toFixed(1);
}
const loadProducts = async function() {
  await Promise.all($('.grid-list .ut2-gl__item ').get().map(async (element) => {
    const url = $(element).find('.ut2-gl__image.ut2-gl__no-image a').attr('href')
    const thumbnail = $(element).find('.ut2-gl__image img').attr('src')
    const title = $(element).find('.ut2-gl__content .ut2-gl__name a').attr('title')
    const price = $(element).find('.ut2-gl__content .ut2-gl__mix-price-and-button .ut2-gl__price .ty-price bdi .ty-price-num').text()
    price.replace(',', '.').replace('đ', '')
    await $.ajax({
      url: url,
      type: 'get'
    })
    return {
      title: title,
      price: price,
      thumbnail: thumbnail,
    }
  }))
  .then(result => console.log(result))
}

loadProducts()
