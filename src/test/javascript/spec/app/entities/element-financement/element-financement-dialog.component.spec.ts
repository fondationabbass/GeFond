/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GeFondTestModule } from '../../../test.module';
import { ElementFinancementDialogComponent } from '../../../../../../main/webapp/app/entities/element-financement/element-financement-dialog.component';
import { ElementFinancementService } from '../../../../../../main/webapp/app/entities/element-financement/element-financement.service';
import { ElementFinancement } from '../../../../../../main/webapp/app/entities/element-financement/element-financement.model';
import { PretService } from '../../../../../../main/webapp/app/entities/pret';

describe('Component Tests', () => {

    describe('ElementFinancement Management Dialog Component', () => {
        let comp: ElementFinancementDialogComponent;
        let fixture: ComponentFixture<ElementFinancementDialogComponent>;
        let service: ElementFinancementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ElementFinancementDialogComponent],
                providers: [
                    PretService,
                    ElementFinancementService
                ]
            })
            .overrideTemplate(ElementFinancementDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ElementFinancementDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElementFinancementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ElementFinancement(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.elementFinancement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'elementFinancementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ElementFinancement();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.elementFinancement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'elementFinancementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
