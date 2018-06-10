/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { CompteComponent } from '../../../../../../main/webapp/app/entities/compte/compte.component';
import { CompteService } from '../../../../../../main/webapp/app/entities/compte/compte.service';
import { Compte } from '../../../../../../main/webapp/app/entities/compte/compte.model';

describe('Component Tests', () => {

    describe('Compte Management Component', () => {
        let comp: CompteComponent;
        let fixture: ComponentFixture<CompteComponent>;
        let service: CompteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [CompteComponent],
                providers: [
                    CompteService
                ]
            })
            .overrideTemplate(CompteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Compte(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.comptes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
