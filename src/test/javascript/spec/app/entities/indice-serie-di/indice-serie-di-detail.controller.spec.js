'use strict';

describe('Controller Tests', function() {

    describe('IndiceSerieDi Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockIndiceSerieDi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockIndiceSerieDi = jasmine.createSpy('MockIndiceSerieDi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'IndiceSerieDi': MockIndiceSerieDi
            };
            createController = function() {
                $injector.get('$controller')("IndiceSerieDiDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'gfpApp:indiceSerieDiUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
