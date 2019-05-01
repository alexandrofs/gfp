'use strict';

describe('Controller Tests', function() {

    describe('Carteira Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCarteira, MockInvestimento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCarteira = jasmine.createSpy('MockCarteira');
            MockInvestimento = jasmine.createSpy('MockInvestimento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Carteira': MockCarteira,
                'Investimento': MockInvestimento
            };
            createController = function() {
                $injector.get('$controller')("CarteiraDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gfpApp:carteiraUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
