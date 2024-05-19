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
    console.log(url)
    const thumbnail = $(element).find('.ut2-gl__image img').attr('src')
    await $.ajax({
      url: url,
      type: 'get'
    })
    return {
      thumbnail: thumbnail,
    }
  }))
  .then(result => console.log(result))
}

loadProducts()
