angular.module('market-front').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/app/';

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            console.log($scope.ProductsPage);
        });
    };

    $scope.addToCart = function (productId) {
        $http.get(contextPath + 'api/v1/cart/add/' + productId)
            .then(function (response) {
            });
    }

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders')
            .then(function (response) {
                $scope.MyOrders = response.data;
            });
    }

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + 'api/v1/products/' + productId)
            .then(function (response) {
                 console.log(response.data)
                 $scope.loadProducts();
            });
    }
                //первая страница по дефолту/сброс фильтра
    $scope.loadProductsDefault = function (pageIndex = 1) {
        $http.get(contextPath + 'api/v1/products')
            .then(function (response) {
                $scope.ProductsPage = response.data;
            });
    }

    $scope.loadProducts();
    $scope.loadOrders();
});