angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1';

        if ($localStorage.springWebUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        }

    $scope.loadProducts = function (pageIndex = 1) {
        $http ({
            url: contextPath + '/products',
            method: 'get',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part: null,
                min_price: $scope.filter ? $scope.filter.min_price: null,
                max_price: $scope.filter ? $scope.filter.max_price: null
                }
            }).then(function (response) {
                $scope.ProductsPage = response.data;
                console.log($scope.ProductsList);
            });
     };

     $scope.tryToAuth = function () {
             $http.post('http://localhost:8189/app/auth', $scope.user)
                 .then(function successCallback(response) {
                     if (response.data.token) {
                         $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                         $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                         $scope.user.username = null;
                         $scope.user.password = null;
                     }
                 }, function errorCallback(response) {
                    console.log(response.data);
                    alert("incorrect login/password");
                 });
         };

         $scope.tryToLogout = function () {
             $scope.clearUser();
             if ($scope.user.username) {
                 $scope.user.username = null;
             }
             if ($scope.user.password) {
                 $scope.user.password = null;
             }
         };

         $scope.clearUser = function () {
             delete $localStorage.springWebUser;
             $http.defaults.headers.common.Authorization = '';
         };

         $rootScope.isUserLoggedIn = function () {
             if ($localStorage.springWebUser) {
                 return true;
             } else {
                 return false;
             }
         };

         $scope.showCurrentUserInfo = function () {
             $http.get('http://localhost:8189/app/api/v1/profile')
                 .then(function successCallback(response) {
                     alert('MY NAME IS: ' + response.data.username);
                 }, function errorCallback(response) {
                     alert('UNAUTHORIZED');
                 });
         };

        //первая страница по дефолту/сброс фильтра
     $scope.loadProductsDefault = function (pageIndex = 1) {
            $http.get(contextPath + '/products')
                .then(function (response) {
                    $scope.ProductsPage = response.data;
                });
     }

        $scope.addToCart = function (productId){
            $http.get('http://localhost:8189/app/api/v1/carts/add/' + productId)
                        .then(function (response) {
                            $scope.loadCart();
                        });
        }

         $scope.loadCart = function (){
             $http.get('http://localhost:8189/app/api/v1/carts')
                        .then(function (response) {
                            $scope.Cart = response.data;
                        });
         }

         $scope.loadOrders = function (){
             $http.get('http://localhost:8189/app/api/v1/orders')
                        .then(function (response) {
                             $scope.MyOrders = response.data;
                        });
         }

         $scope.clearCart = function () {
            $http.delete('http://localhost:8189/app/api/v1/carts/clear')
                                  .then(function (response) {
                                      console.log(response.data)
                                      $scope.loadCart();
                                  });
         }

         $scope.removeItem = function (productId) {
                $http.get('http://localhost:8189/app/api/v1/carts/remove/' + productId)
                         .then(function (response) {
                             console.log(response.data)
                             $scope.loadCart();
                         });
         }

        $scope.deleteProduct = function (productId) {
            $http.delete(contextPath + '/products/' + productId)
                .then(function (response) {
                    console.log(response.data)
                    $scope.loadProducts();
                });
     }
         $scope.createOrder = function () {
             $http ({
                 url: 'http://localhost:8189/app/api/v1/orders',
                 method: 'POST',
                 data: $scope.orderDetails
             }).then(function (response) {
                 console.log(response.data)
                 alert("Order completed");
                 $scope.loadOrders();
                 $scope.loadCart();
                 $scope.orderDetails = null;
             });
     }
//
//        $scope.createProductJson = function () {
//            console.log($scope.newProductJson);
//            $http.post(contextPath + '/products/', $scope.newProductJson)
//                           .then(function (response) {
//                                $scope.loadProducts();
//                           });
//     }
//
//        $scope.updateProduct = function () {
//            $http.put(contextPath + '/products', $scope.updated_product)
//                            .then(function (response) {
//                                console.log($scope.updated_product);
//                                $scope.loadProducts();
//                                $scope.updated_product = null;
//                             });
//     }
//
//        $scope.prepareProductForUpdate = function (productId) {
//            $http.get(contextPath + '/products/' + productId)
//                        .then(function (response) {
//                                $scope.updated_product = response.data;
//                             });
//     }
        $scope.loadProducts();
        $scope.loadCart();
        $scope.loadOrders();
});