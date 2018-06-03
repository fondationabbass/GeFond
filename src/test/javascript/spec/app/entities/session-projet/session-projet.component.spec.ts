/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { SessionProjetComponent } from '../../../../../../main/webapp/app/entities/session-projet/session-projet.component';
import { SessionProjetService } from '../../../../../../main/webapp/app/entities/session-projet/session-projet.service';
import { SessionProjet } from '../../../../../../main/webapp/app/entities/session-projet/session-projet.model';

describe('Component Tests', () => {

    describe('SessionProjet Management Component', () => {
        let comp: SessionProjetComponent;
        let fixture: ComponentFixture<SessionProjetComponent>;
        let service: SessionProjetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [SessionProjetComponent],
                providers: [
                    SessionProjetService
                ]
            })
            .overrideTemplate(SessionProjetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SessionProjetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SessionProjetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SessionProjet(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sessionProjets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
