

        // Scroll progress + navbar
        // const bar = document.getElementById('spbar'), nav = document.getElementById('navbar');
        // window.addEventListener('scroll', () => {
        //     bar.style.width = (window.scrollY / (document.body.scrollHeight - window.innerHeight) * 100) + '%';
        //     nav.classList.toggle('sc', window.scrollY > 60);
        // });

        // Mobile nav
        const mobileNav = document.getElementById('mobileNav');
        const hambBtn = document.getElementById('hambBtn');
        let navOpen = false;
        function toggleNav() {
            navOpen = !navOpen;
            mobileNav.classList.toggle('open', navOpen);
            hambBtn.classList.toggle('open', navOpen);
            document.body.style.overflow = navOpen ? 'hidden' : '';
        }
        function closeNav() {
            navOpen = false;
            mobileNav.classList.remove('open');
            hambBtn.classList.remove('open');
            document.body.style.overflow = '';
        }
        document.addEventListener('click', (e) => {
            if (navOpen && !mobileNav.contains(e.target) && !hambBtn.contains(e.target)) closeNav();
        });

        // Reveal animations
        const obs = new IntersectionObserver(entries => {
            entries.forEach(e => {
                if (e.isIntersecting) {
                    const sibs = [...e.target.parentElement.querySelectorAll('.rv')];
                    setTimeout(() => e.target.classList.add('show'), sibs.indexOf(e.target) * 70);
                    obs.unobserve(e.target);
                }
            });
        }, { threshold: .08 });
        document.querySelectorAll('.rv').forEach(el => obs.observe(el));

        /* ── SMOOTH SCROLL ── */
        // const SS = (() => {
        //     const EASE = 0.092, WMULT = 1.1, MAXD = 180, FRIC = 0.88;
        //     let cur = window.scrollY, tgt = window.scrollY, vel = 0, raf = null, isTo = false, touching = false, ty = 0;
        //     function easeOut(t) { return 1 - Math.pow(1 - t, 3); }
        //     function clamp(v, a, b) { return Math.min(Math.max(v, a), b); }
        //     function max() { return document.documentElement.scrollHeight - window.innerHeight; }
        //     function loop() { const d = tgt - cur; if (Math.abs(d) < 0.5 && Math.abs(vel) < 0.5) { cur = tgt; window.scrollTo(0, cur); raf = null; return; } cur += d * EASE + vel; vel *= FRIC; cur = clamp(cur, 0, max()); window.scrollTo(0, cur); raf = requestAnimationFrame(loop); }
        //     function go() { if (!raf) raf = requestAnimationFrame(loop); }
        //     window.addEventListener('wheel', e => { if (isTo) return; e.preventDefault(); let d = e.deltaY; if (e.deltaMode === 1) d *= 32; if (e.deltaMode === 2) d *= window.innerHeight; d = clamp(d, -MAXD, MAXD) * WMULT; tgt = clamp(tgt + d, 0, max()); go(); }, { passive: false });
        //     window.addEventListener('touchstart', e => { touching = true; ty = e.touches[0].clientY; vel = 0; }, { passive: true });
        //     window.addEventListener('touchmove', e => { if (!touching) return; const dy = (ty - e.touches[0].clientY); ty = e.touches[0].clientY; tgt = clamp(tgt + dy, 0, max()); go(); }, { passive: true });
        //     window.addEventListener('touchend', () => { touching = false; });
        //     window.addEventListener('keydown', e => { if (isTo) return; const map = { ArrowDown: 100, ArrowUp: -100, PageDown: window.innerHeight * .85, PageUp: -(window.innerHeight * .85), ' ': window.innerHeight * .85 }; if (map[e.key] !== undefined) { e.preventDefault(); tgt = clamp(tgt + map[e.key], 0, max()); go(); } if (e.key === 'Home') { tgt = 0; go(); } if (e.key === 'End') { tgt = max(); go(); } });
        //     function scrollTo(dest, dur) { dur = dur || 900; isTo = true; const sy = cur, dist = dest - sy, st = performance.now(); if (raf) { cancelAnimationFrame(raf); raf = null; } function anim(now) { const p = Math.min((now - st) / dur, 1), ea = easeOut(p); cur = sy + dist * ea; tgt = cur; window.scrollTo(0, cur); if (p < 1) { raf = requestAnimationFrame(anim); } else { cur = dest; tgt = dest; window.scrollTo(0, dest); raf = null; isTo = false; } } raf = requestAnimationFrame(anim); }
        //     document.addEventListener('click', e => { const a = e.target.closest('a[href^="#"]'); if (!a) return; const id = a.getAttribute('href').slice(1); if (!id) { e.preventDefault(); scrollTo(0); return; } const el = document.getElementById(id); if (!el) return; e.preventDefault(); scrollTo(clamp(el.getBoundingClientRect().top + window.scrollY - 80, 0, max())); });
        //     window.addEventListener('resize', () => { tgt = window.scrollY; cur = window.scrollY; });
        //     return { scrollTo };
        // })();
