  
    // Scroll progress bar + navbar
    // const bar = document.getElementById('spbar'), nav = document.getElementById('navbar');
    // window.addEventListener('scroll', () => {
    //   bar.style.width = (window.scrollY / (document.body.scrollHeight - window.innerHeight) * 100) + '%';
    //   nav.classList.toggle('sc', window.scrollY > 60);
    // });

    // Countdown
    function tick() {
      const ms = new Date('2025-09-28T09:00:00') - new Date(),
        p = n => String(Math.max(0, Math.floor(n))).padStart(2, '0');
      document.getElementById('cdd').textContent = p(ms / 86400000);
      document.getElementById('cdh').textContent = p(ms % 86400000 / 3600000);
      document.getElementById('cdm').textContent = p(ms % 3600000 / 60000);
      document.getElementById('cds').textContent = p(ms % 60000 / 1000);
    }
    tick(); setInterval(tick, 1000);

    // Schedule tabs
    function st(btn, id) {
      document.querySelectorAll('.tab').forEach(b => b.classList.remove('on'));
      document.querySelectorAll('.tp').forEach(p => p.classList.remove('on'));
      btn.classList.add('on');
      document.getElementById(id).classList.add('on');
    }

    // Mobile nav toggle
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

    // Close mobile nav on outside click
    mobileNav.addEventListener('click', function (e) {
      if (e.target === mobileNav) closeNav();
    });

    // Intersection observer for reveal animations
    const obs = new IntersectionObserver(entries => {
      entries.forEach(e => {
        if (e.isIntersecting) {
          const sibs = [...e.target.parentElement.querySelectorAll('.rv')];
          setTimeout(() => e.target.classList.add('show'), sibs.indexOf(e.target) * 80);
          obs.unobserve(e.target);
        }
      });
    }, { threshold: .1 });
    document.querySelectorAll('.rv').forEach(el => obs.observe(el));

    // Active nav link on scroll
    window.addEventListener('scroll', () => {
      let cur = '';
      document.querySelectorAll('section[id]').forEach(s => {
        if (window.scrollY >= s.offsetTop - 130) cur = s.id;
      });
      document.querySelectorAll('.nav-ul a').forEach(a => {
        a.classList.toggle('active', a.getAttribute('href') === '#' + cur);
      });
    });

    // Contact form
    function sendMsg(b) {
      b.textContent = 'Sending...'; b.disabled = true;
      setTimeout(() => { b.textContent = '✓ Message Sent!'; b.style.background = '#1a3a28'; }, 1500);
    }

    /* ─────────────────────────────────────────
       ULTRA SMOOTH SCROLL ENGINE
       Custom easing + momentum-based scrolling
    ───────────────────────────────────────── */
    // const SmoothScroll = (() => {
    //   const EASE_FACTOR = 0.092;
    //   const WHEEL_MULT = 1.1;
    //   const TOUCH_MULT = 1.0;
    //   const MAX_DELTA = 180;
    //   const FRICTION = 0.88;

    //   let current = window.scrollY;
    //   let target = window.scrollY;
    //   let velocity = 0;
    //   let raf = null;
    //   let touching = false;
    //   let touchY = 0;
    //   let isScrollingTo = false;

    //   function easeOutCubic(t) { return 1 - Math.pow(1 - t, 3); }
    //   function clamp(v, mn, mx) { return Math.min(Math.max(v, mn), mx); }
    //   function maxScroll() { return document.documentElement.scrollHeight - window.innerHeight; }

    //   function loop() {
    //     const diff = target - current;
    //     if (Math.abs(diff) < 0.5 && Math.abs(velocity) < 0.5) {
    //       current = target;
    //       window.scrollTo(0, current);
    //       raf = null;
    //       return;
    //     }
    //     current += diff * EASE_FACTOR + velocity;
    //     velocity *= FRICTION;
    //     current = clamp(current, 0, maxScroll());
    //     window.scrollTo(0, current);
    //     raf = requestAnimationFrame(loop);
    //   }

    //   function start() { if (!raf) raf = requestAnimationFrame(loop); }

    //   window.addEventListener('wheel', (e) => {
    //     if (isScrollingTo) return;
    //     e.preventDefault();
    //     let delta = e.deltaY;
    //     if (e.deltaMode === 1) delta *= 32;
    //     if (e.deltaMode === 2) delta *= window.innerHeight;
    //     delta = clamp(delta, -MAX_DELTA, MAX_DELTA) * WHEEL_MULT;
    //     target = clamp(target + delta, 0, maxScroll());
    //     start();
    //   }, { passive: false });

    //   window.addEventListener('touchstart', (e) => {
    //     touching = true;
    //     touchY = e.touches[0].clientY;
    //     velocity = 0;
    //   }, { passive: true });

    //   window.addEventListener('touchmove', (e) => {
    //     if (!touching) return;
    //     const dy = (touchY - e.touches[0].clientY) * TOUCH_MULT;
    //     touchY = e.touches[0].clientY;
    //     target = clamp(target + dy, 0, maxScroll());
    //     start();
    //   }, { passive: true });

    //   window.addEventListener('touchend', () => { touching = false; });

    //   window.addEventListener('keydown', (e) => {
    //     if (isScrollingTo) return;
    //     const map = {
    //       ArrowDown: 100, ArrowUp: -100,
    //       PageDown: window.innerHeight * 0.85,
    //       PageUp: -(window.innerHeight * 0.85),
    //       ' ': window.innerHeight * 0.85,
    //     };
    //     if (map[e.key] !== undefined) {
    //       e.preventDefault();
    //       target = clamp(target + map[e.key], 0, maxScroll());
    //       start();
    //     }
    //     if (e.key === 'Home') { target = 0; start(); }
    //     if (e.key === 'End') { target = maxScroll(); start(); }
    //   });

    //   function scrollTo(destY, duration) {
    //     duration = duration || 900;
    //     isScrollingTo = true;
    //     const startY = current;
    //     const dist = destY - startY;
    //     const startT = performance.now();
    //     if (raf) { cancelAnimationFrame(raf); raf = null; }

    //     function animateTo(now) {
    //       const elapsed = now - startT;
    //       const progress = Math.min(elapsed / duration, 1);
    //       const eased = easeOutCubic(progress);
    //       current = startY + dist * eased;
    //       target = current;
    //       window.scrollTo(0, current);
    //       if (progress < 1) {
    //         raf = requestAnimationFrame(animateTo);
    //       } else {
    //         current = destY; target = destY;
    //         window.scrollTo(0, destY);
    //         raf = null; isScrollingTo = false;
    //       }
    //     }
    //     raf = requestAnimationFrame(animateTo);
    //   }

    //   document.addEventListener('click', (e) => {
    //     const a = e.target.closest('a[href^="#"]');
    //     if (!a) return;
    //     const id = a.getAttribute('href').slice(1);
    //     if (!id) { e.preventDefault(); scrollTo(0); return; }
    //     const el = document.getElementById(id);
    //     if (!el) return;
    //     e.preventDefault();
    //     const offset = el.getBoundingClientRect().top + window.scrollY - 80;
    //     scrollTo(clamp(offset, 0, maxScroll()));
    //     closeNav();
    //   });

    //   window.addEventListener('resize', () => {
    //     target = window.scrollY;
    //     current = window.scrollY;
    //   });

    //   return { scrollTo };
    // })();
  