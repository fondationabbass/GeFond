/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GeFondTestModule } from '../../../test.module';
import { SessionProjetDetailComponent } from '../../../../../../main/webapp/app/entities/session-projet/session-projet-detail.component';
import { SessionProjetService } from '../../../../../../main/webapp/app/entities/session-projet/session-projet.service';
import { SessionProjet } from '../../../../../../main/webapp/app/entities/session-projet/session-projet.model';

describe('Component Tests', () => {

    describe('SessionProjet Management Detail Component', () => {
        let comp: SessionProjetDetailComponent;
        let fixture: ComponentFixture<SessionProjetDetailComponent>;
        let service: SessionProjetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [SessionProjetDetailComponent],
                providers: [
                    SessionProjetService
                ]
            })
            .overrideTemplate(SessionProjetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SessionProjetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SessionProjetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SessionProjet(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sessionProjet).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
