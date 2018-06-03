/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GeFondTestModule } from '../../../test.module';
import { ExperienceCandidatDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat-delete-dialog.component';
import { ExperienceCandidatService } from '../../../../../../main/webapp/app/entities/experience-candidat/experience-candidat.service';

describe('Component Tests', () => {

    describe('ExperienceCandidat Management Delete Component', () => {
        let comp: ExperienceCandidatDeleteDialogComponent;
        let fixture: ComponentFixture<ExperienceCandidatDeleteDialogComponent>;
        let service: ExperienceCandidatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ExperienceCandidatDeleteDialogComponent],
                providers: [
                    ExperienceCandidatService
                ]
            })
            .overrideTemplate(ExperienceCandidatDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExperienceCandidatDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceCandidatService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
