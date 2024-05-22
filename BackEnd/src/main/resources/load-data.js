'use strict';

const loadProducts = async function () {
  await Promise.all(
      $('.grid-list .ut2-gl__item ').get().map(async (element) => {
        const url = $(element).find(
            '.ut2-gl__body.content-on-hover .ut2-gl__image a').attr(
            'href')
        const thumbnail = $(element).find('.ut2-gl__image img').attr('src')
        const title = $(element).find('.ut2-gl__content .ut2-gl__name a').attr(
            'title')
        var code, description, price;
        var productinfo = {};
        var images = [];
        await $.ajax({
          url: url,
          type: 'get',
          success: function (data) {
            var $html = $(data);
            code = $html.find('meta[itemprop="sku"]').attr('content');
            description = $html.find('meta[itemprop="description').attr(
                'content')
            price = $html.find('meta[itemprop=price]').attr('content');
            $html.find('meta[itemprop=image]').each(function () {
              var imgUrl = $(this).attr('content');
              images.push(imgUrl)
            })

            $html.find('.ty-product-feature').each(function() {
              var featureLabel = $(this).find('.ty-product-feature__label span').text().trim();
              var featureValue = $(this).find('.ty-product-feature__value').text().trim();
              productinfo[featureLabel] = featureValue;
            });
          }
        })
        return {
          code: code,
          title: title,
          price: price,
          thumbnail: thumbnail,
          description: description,
          images: images,
          productInfo: productinfo
        }
      }))
  .then(result => console.log(result))
}

loadProducts();
