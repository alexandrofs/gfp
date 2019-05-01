(function() {
    'use strict';

    angular
        .module('gfpApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carteira', {
            parent: 'entity',
            url: '/carteira',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.carteira.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carteira/carteiras.html',
                    controller: 'CarteiraController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carteira');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('carteira-detail', {
            parent: 'carteira',
            url: '/carteira/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.carteira.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carteira/carteira-detail.html',
                    controller: 'CarteiraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carteira');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Carteira', function($stateParams, Carteira) {
                    return Carteira.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'carteira',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('carteira-detail.edit', {
            parent: 'carteira-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carteira/carteira-dialog.html',
                    controller: 'CarteiraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carteira', function(Carteira) {
                            return Carteira.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carteira.new', {
            parent: 'carteira',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carteira/carteira-dialog.html',
                    controller: 'CarteiraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('carteira', null, { reload: 'carteira' });
                }, function() {
                    $state.go('carteira');
                });
            }]
        })
        .state('carteira.edit', {
            parent: 'carteira',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carteira/carteira-dialog.html',
                    controller: 'CarteiraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carteira', function(Carteira) {
                            return Carteira.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carteira', null, { reload: 'carteira' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carteira.delete', {
            parent: 'carteira',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carteira/carteira-delete-dialog.html',
                    controller: 'CarteiraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Carteira', function(Carteira) {
                            return Carteira.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carteira', null, { reload: 'carteira' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
