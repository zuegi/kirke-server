# Kirke Event Sourcing Server


## Create FoodCart
Topic: kirke/foodcart/6bf9a23d-487c-42dc-93ee-d73c6153a8f6/aggregate

Create FoodCart
```json
{
   "foodCartId": "6bf9a23d-487c-42dc-93ee-d73c6153a8f6"
}
```
Add SelectedProduct
```json
{
  "foodCartId": "6bf9a23d-487c-42dc-93ee-d73c6153a8f6",
  "productId": "044619bc-146e-4065-a4b8-34ed49d8b76a",
  "quantity": 1
}
```
Confirm FoodCart
```json
{
   "foodCartId": "6bf9a23d-487c-42dc-93ee-d73c6153a8f6"
}
```

## Find foodCart by targetId
Topic: kirke/foodcart/6bf9a23d-487c-42dc-93ee-d73c6153a8f6/request

## Solace Browser 
![Request Reply in Solace Browser](./doc/images/Solace-Console-Request-Reply.png)




https://codelabs.solace.dev/codelabs/spring-cloud-stream-beyond/index.html?index=..%2F..index#2

Spring Cloud Stream - functional and reactive
https://spring.io/blog/2019/10/17/spring-cloud-stream-functional-and-reactive

Spring Cloud Stream Request/Reply with Solace
https://github.com/Mrc0113/cloud-stream-request-reply-solace
