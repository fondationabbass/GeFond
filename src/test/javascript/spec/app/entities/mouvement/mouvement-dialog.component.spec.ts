/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GeFondTestModule } from '../../../test.module';
import { MouvementDialogComponent } from '../../../../../../main/webapp/app/entities/mouvement/mouvement-dialog.component';
import { MouvementService } from '../../../../../../main/webapp/app/entities/mouvement/mouvement.service';
import { Mouvement } from '../../../../../../main/webapp/app/entities/mouvement/mouvement.model';
import { CompteService } from '../../../../../../main/webapp/app/entities/compte';
import { OperationService } from '../../../../../../main/webapp/app/entities/operation';

describe('Component Tests', () => {

    describe('Mouvement Management Dialog Component', () => {
        let comp: MouvementDialogComponent;
        let fixture: ComponentFixture<MouvementDialogComponent>;
        let service: MouvementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [MouvementDialogComponent],
                providers: [
                    CompteService,
                    OperationService,
                    MouvementService
                ]
            })
            .overrideTemplate(MouvementDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MouvementDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MouvementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Mouvement(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.mouvement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mouvementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Mouvement();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.mouvement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mouvementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
