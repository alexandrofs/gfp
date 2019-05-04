/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GfpTestModule } from '../../../test.module';
import { CarteiraUpdateComponent } from 'app/entities/carteira/carteira-update.component';
import { CarteiraService } from 'app/entities/carteira/carteira.service';
import { Carteira } from 'app/shared/model/carteira.model';

describe('Component Tests', () => {
    describe('Carteira Management Update Component', () => {
        let comp: CarteiraUpdateComponent;
        let fixture: ComponentFixture<CarteiraUpdateComponent>;
        let service: CarteiraService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GfpTestModule],
                declarations: [CarteiraUpdateComponent]
            })
                .overrideTemplate(CarteiraUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CarteiraUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarteiraService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Carteira(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.carteira = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Carteira();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.carteira = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
