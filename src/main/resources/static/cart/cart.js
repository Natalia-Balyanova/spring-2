angular.module('market-front').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/app/';

    $scope.loadCart = function () {
        $http({
            url: contextPath + 'api/v1/cart',
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.disabledCheckOut = function () {
        alert("You must be logged in to place an order");
    }

    $scope.removeItem = function (productId) {
        $http ({
            url: contextPath + 'api/v1/cart/remove/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.incrementItem = function (productId) {
        $http ({
            url: contextPath + 'api/v1/cart/add/' + productId,
            method: 'GET'
        }).then(function (response) {
                $scope.loadCart();
        });
    }

    $scope.decrementItem = function (productId) {
        $http ({
            url: contextPath + 'api/v1/cart/decrement/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.checkOut = function () {
        $http ({
              url: contextPath + 'api/v1/orders',
              method: 'POST',
              data: $scope.orderDetails
        }).then(function (response) {
              console.log(response.data)
              alert("Order completed");
              $scope.loadCart();
              $scope.orderDetails = null;
              });
    }

    $scope.loadCart();
});