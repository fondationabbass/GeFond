/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { ProjetComponent } from '../../../../../../main/webapp/app/entities/projet/projet.component';
import { ProjetService } from '../../../../../../main/webapp/app/entities/projet/projet.service';
import { Projet } from '../../../../../../main/webapp/app/entities/projet/projet.model';

describe('Component Tests', () => {

    describe('Projet Management Component', () => {
        let comp: ProjetComponent;
        let fixture: ComponentFixture<ProjetComponent>;
        let service: ProjetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ProjetComponent],
                providers: [
                    ProjetService
                ]
            })
            .overrideTemplate(ProjetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Projet(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.projets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
